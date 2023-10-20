/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.util;

import com.google.common.collect.Lists;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.plants.BlockMushroomTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.te.TECropBase;
import net.dries007.tfc.objects.te.TEPlacedItemFlat;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.worldgen.WorldGenBerryBushes;
import net.dries007.tfc.world.classic.worldgen.WorldGenPlantTFC;
import net.dries007.tfc.world.classic.worldgen.WorldGenTrees;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tfcflorae.util.RegenWildCropsTFCF;

import java.util.*;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

/**
 * Seasonally regenerates rocks, sticks, snow, plants, crops and bushes.
 */

//@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = MOD_ID)
public class WorldRegenHandler {

    public static final WorldGenPlantTFC PLANT_GEN = new WorldGenPlantTFC();
    private static final RegenRocksSticks ROCKS_GEN = new RegenRocksSticks(true);
    private static final RegenWildCrops CROPS_GEN = new RegenWildCrops();
    private static final RegenWildCropsTFCF CROPSTFCF_GEN = new RegenWildCropsTFCF();
    private static final WorldGenBerryBushes BUSH_GEN = new WorldGenBerryBushes();
    private static final Random RANDOM = new Random();
    private static final List<ChunkPos> POSITIONS = new LinkedList<>();


    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event) {
        ChunkDataTFC chunkDataTFC = ChunkDataTFC.get(event.getChunk());
        if (event.getWorld().provider.getDimension() == 0 && chunkDataTFC.isInitialized() && POSITIONS.size() < 1000) {
            //Only run this in the early months of each year
            if (CalendarTFC.CALENDAR_TIME.getMonthOfYear().isWithin(Month.APRIL, Month.JULY) && !chunkDataTFC.isSpawnProtected() && CalendarTFC.CALENDAR_TIME.getTotalYears() > chunkDataTFC.getLastUpdateYear()) {
                POSITIONS.add(event.getChunk().getPos());
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (!event.world.isRemote && event.phase == TickEvent.Phase.END && !POSITIONS.isEmpty()) {
            double tps = Helpers.getTPS(event.world, 0);
            ChunkPos pos = (ChunkPos) POSITIONS.remove(0);
            if (tps > (double) ConfigTFC.General.WORLD_REGEN.minRegenTps) {
                Chunk chunk = event.world.getChunk(pos.x, pos.z);
                BlockPos blockPos = pos.getBlock(0, 0, 0);
                ChunkDataTFC chunkDataTFC = ChunkDataTFC.get(event.world, pos.getBlock(0, 0, 0));
                IChunkProvider chunkProvider = event.world.getChunkProvider();
                IChunkGenerator chunkGenerator = ((ChunkProviderServer) chunkProvider).chunkGenerator;
                if (CalendarTFC.CALENDAR_TIME.getMonthOfYear().isWithin(Month.APRIL, Month.JULY) && !chunkDataTFC.isSpawnProtected() && CalendarTFC.CALENDAR_TIME.getTotalYears() > chunkDataTFC.getLastUpdateYear()) {
                    int worldZ;
                    if (ConfigTFC.General.WORLD_REGEN.sticksRocksModifier > 0.0) {
                        removeAllPlacedItems(event.world, pos);
                        double rockModifier = ConfigTFC.General.WORLD_REGEN.sticksRocksModifier;
                        ROCKS_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                        float density = chunkDataTFC.getFloraDensity();
                        List<Tree> trees = chunkDataTFC.getValidTrees();
                        worldZ = 3 + (int) ((double) (4.0F * density) + (double) (1.5F * (float) trees.size()) * rockModifier);
                        if (trees.isEmpty()) {
                            worldZ = 1 + (int) ((double) (1.5F * density) * rockModifier);
                        }

                        WorldGenTrees.generateLooseSticks(RANDOM, pos.x, pos.z, event.world, worldZ);
                    }

                    removeCropsAndMushrooms(event.world, pos);
                    removeSeedBags(event.world, pos);
                    float floraDensity = chunkDataTFC.getFloraDensity();
                    float floraDiversity = chunkDataTFC.getFloraDiversity();
                    Plant mushroom = (Plant) TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI);
                    if (mushroom != null) {
                        PLANT_GEN.setGeneratedPlant(mushroom);
                    }

                    for (float i = (float) RANDOM.nextInt(Math.round(3.0F / floraDiversity)); i < (1.0F + floraDensity) * 5.0F; ++i) {
                        BlockPos blockMushroomPos = event.world.getHeight(blockPos.add(RANDOM.nextInt(16) + 8, 0, RANDOM.nextInt(16) + 8));
                        PLANT_GEN.generate(event.world, RANDOM, blockMushroomPos);
                    }

                    CROPS_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                    CROPSTFCF_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                    BUSH_GEN.generate(RANDOM, pos.x, pos.z, event.world, chunkGenerator, chunkProvider);
                    int worldX = pos.x << 4;
                    worldZ = pos.z << 4;
                    BlockPos blockpos = new BlockPos(worldX, 0, worldZ);
                    Biome biome = event.world.getBiome(blockpos.add(16, 0, 16));
                    regenPredators(event.world, biome, worldX + 8, worldZ + 8, 16, 16, RANDOM);
                    chunkDataTFC.resetLastUpdateYear();
                }

                chunk.markDirty();
                ((ChunkProviderServer) chunkProvider).queueUnload(chunk);
            }
        }

    }

    private static void removeCropsAndMushrooms(World world, ChunkPos pos) {
        for (int xX = 0; xX < 16; ++xX) {
            for (int zZ = 0; zZ < 16; ++zZ) {
                BlockPos topPos = world.getTopSolidOrLiquidBlock(pos.getBlock(xX, 0, zZ));
                IBlockState topState = world.getBlockState(topPos);
                Block topBlock = topState.getBlock();
                if (!topState.getMaterial().isLiquid() && (topBlock instanceof BlockCropDead || topBlock instanceof BlockMushroomTFC)) {
                    IBlockState soil = world.getBlockState(topPos.down());
                    if (soil.getBlock() instanceof BlockRockVariant) {
                        BlockRockVariant soilRock = (BlockRockVariant) soil.getBlock();
                        if (soilRock.getType() != Rock.Type.FARMLAND) {
                            world.removeTileEntity(topPos);
                            world.setBlockToAir(topPos);
                        }
                    }
                }
            }
        }

        Map<BlockPos, TileEntity> teTargets = world.getChunk(pos.x, pos.z).getTileEntityMap();
        List<BlockPos> removals = new ArrayList();
        teTargets.forEach((tePosx, te) -> {
            IBlockState state = world.getBlockState(tePosx);
            if (te instanceof TECropBase && state.getProperties().containsKey(BlockCropTFC.WILD) && (Boolean) state.getValue(BlockCropTFC.WILD)) {
                removals.add(tePosx);
            }

        });
        Iterator var11 = removals.iterator();

        while (var11.hasNext()) {
            BlockPos tePos = (BlockPos) var11.next();
            world.removeTileEntity(tePos);
            world.setBlockToAir(tePos);
        }

    }

    private static void removeSeedBags(World world, ChunkPos pos) {
        List<Entity> removals = new ArrayList();
        ClassInheritanceMultiMap[] var3 = world.getChunk(pos.x, pos.z).getEntityLists();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ClassInheritanceMultiMap<Entity> target = var3[var5];
            target.forEach((entity) -> {
                if (entity instanceof EntityItem && ((EntityItem) entity).getItem().getItem() instanceof ItemSeedsTFC) {
                    removals.add(entity);
                }

            });
        }

        Iterator var7 = removals.iterator();

        while (var7.hasNext()) {
            Entity e = (Entity) var7.next();
            world.removeEntity(e);
        }

    }

    private static void removeAllPlacedItems(World world, ChunkPos pos) {
        List<BlockPos> removals = new ArrayList();
        world.getChunk(pos.x, pos.z).getTileEntityMap().forEach((tePosx, te) -> {
            if (te instanceof TEPlacedItemFlat) {
                removals.add(tePosx);
            }

        });
        Iterator var3 = removals.iterator();

        while (var3.hasNext()) {
            BlockPos tePos = (BlockPos) var3.next();
            world.removeTileEntity(tePos);
            world.setBlockToAir(tePos);
        }

    }

    public static void regenPredators(World worldIn, Biome biomeIn, int centerX, int centerZ, int diameterX, int diameterZ, Random randomIn) {
        BlockPos chunkBlockPos = new BlockPos(centerX, 0, centerZ);
        float temperature = ClimateTFC.getAvgTemp(worldIn, chunkBlockPos);
        float rainfall = ChunkDataTFC.getRainfall(worldIn, chunkBlockPos);
        float floraDensity = ChunkDataTFC.getFloraDensity(worldIn, chunkBlockPos);
        float floraDiversity = ChunkDataTFC.getFloraDiversity(worldIn, chunkBlockPos);
        ForgeRegistries.ENTITIES.getValuesCollection().stream().filter((x) -> {
            if (ICreatureTFC.class.isAssignableFrom(x.getEntityClass())) {
                Entity ent = x.newInstance(worldIn);
                if (ent instanceof IPredator || ent instanceof IHuntable) {
                    int weight = ((ICreatureTFC) ent).getSpawnWeight(biomeIn, temperature, rainfall, floraDensity, floraDiversity);
                    return weight > 0 && randomIn.nextInt(weight) == 0;
                }
            }

            return false;
        }).findAny().ifPresent((entityEntry) -> {
            doGroupSpawning(entityEntry, worldIn, centerX, centerZ, diameterX, diameterZ, randomIn);
        });
    }

    private static void doGroupSpawning(EntityEntry entityEntry, World worldIn, int centerX, int centerZ, int diameterX, int diameterZ, Random rand) {
        List<EntityLiving> group = Lists.newArrayList();
        EntityLiving creature = (EntityLiving) entityEntry.newInstance(worldIn);
        if (creature instanceof ICreatureTFC) {
            ICreatureTFC creatureTFC = (ICreatureTFC) creature;
            int fallback = 5;
            int individuals = Math.max(1, creatureTFC.getMinGroupSize()) + rand.nextInt(creatureTFC.getMaxGroupSize() - Math.max(0, creatureTFC.getMinGroupSize() - 1));

            while (individuals > 0) {
                int i = centerX + rand.nextInt(diameterX);
                int k = centerZ + rand.nextInt(diameterZ);
                BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(i, 0, k));
                creature.setLocationAndAngles((double) ((float) i + 0.5F), (double) blockpos.getY(), (double) ((float) k + 0.5F), rand.nextFloat() * 360.0F, 0.0F);
                if (creature.getCanSpawnHere()) {
                    if (ForgeEventFactory.canEntitySpawn(creature, worldIn, (float) i + 0.5F, (float) blockpos.getY(), (float) k + 0.5F, (MobSpawnerBaseLogic) null) == Event.Result.DENY) {
                        --fallback;
                        if (fallback <= 0) {
                            break;
                        }
                    } else {
                        fallback = 5;
                        worldIn.spawnEntity(creature);
                        group.add(creature);
                        creature.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(creature)), (IEntityLivingData) null);
                        --individuals;
                        if (individuals > 0) {
                            creature = (EntityLiving) entityEntry.newInstance(worldIn);
                            creatureTFC = (ICreatureTFC) creature;
                        }
                    }
                } else {
                    --fallback;
                    if (fallback <= 0) {
                        break;
                    }
                }
            }

            creatureTFC.getGroupingRules().accept(group, rand);
        }

    }
}

