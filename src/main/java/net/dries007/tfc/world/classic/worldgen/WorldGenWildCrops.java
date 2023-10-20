/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.plants.BlockTallGrassWater;
import tfcflorae.types.PlantsTFCF;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@ParametersAreNonnullByDefault
public class WorldGenWildCrops implements IWorldGenerator {
    private static final List<ICrop> CROPS = new ArrayList<>();

    public static void register(ICrop bush) {
        CROPS.add(bush);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkGenerator instanceof ChunkGenTFC && world.provider.getDimension() == 0 && CROPS.size() > 0 && ConfigTFC.General.FOOD.cropRarity > 0) {
            if (random.nextInt(ConfigTFC.General.FOOD.cropRarity) == 0) {
                // Guarantees crop generation if possible (easier to balance by config file while also making it random)
                BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

                Collections.shuffle(CROPS);
                ChunkDataTFC data = ChunkDataTFC.get(world, chunkBlockPos);
                float temperature = ClimateTFC.getAvgTemp(world, chunkBlockPos);
                float rainfall = ChunkDataTFC.getRainfall(world, chunkBlockPos);
                float floraDensity = data.getFloraDensity();
                float floraDiversity = data.getFloraDiversity();

                Biome b = world.getBiome(chunkBlockPos);
                ICrop crop = CROPS.stream().filter(x -> x.isValidConditions(temperature, rainfall)).findFirst().orElse(null);
                if (crop != null) {
                    BlockCropTFC cropBlock;
                    int cropsInChunk;
                    int maxStage;
                    if (random.nextInt(ConfigTFC.General.FOOD.cropRarity) == 0) {
                        cropBlock = BlockCropTFC.get(crop);
                        cropsInChunk = 3 + random.nextInt(5);

                        for (int i = 0; i < cropsInChunk; ++i) {
                            int x = (chunkX << 4) + random.nextInt(16) + 8;
                            int z = (chunkZ << 4) + random.nextInt(16) + 8;
                            BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
                            if (isValidPosition(world, pos)) {
                                double yearProgress = (double) CalendarTFC.CALENDAR_TIME.getMonthOfYear().ordinal() / 11.0;
                                maxStage = crop.getMaxStage();
                                int growth = (int) (yearProgress * (double) maxStage) + 3 - random.nextInt(2);
                                if (growth > maxStage) {
                                    growth = maxStage;
                                }

                                world.setBlockState(pos, cropBlock.getDefaultState().withProperty(cropBlock.getStageProperty(), growth).withProperty(BlockCropTFC.WILD, true), 2);
                            }
                        }
                    }

                    if (crop == Crop.BARLEY || crop == Crop.MAIZE || crop == Crop.OAT || crop == Crop.RYE || crop == Crop.WHEAT) {
                        cropBlock = BlockCropTFC.get(crop);

                        for (cropsInChunk = random.nextInt(Math.round(1.0F / floraDiversity)); (float) cropsInChunk < (4.0F + floraDensity + floraDiversity) * (float) (10 + random.nextInt(10)); ++cropsInChunk) {
                            BlockPos pos = world.getHeight(chunkBlockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
                            if (isValidPosition(world, pos) && (double) floraDensity <= Math.abs(0.20000000298023224 - random.nextGaussian() / 20.0) && b == BiomesTFC.FIELDS) {
                                double yearProgress = (double) CalendarTFC.CALENDAR_TIME.getMonthOfYear().ordinal() / 11.0;
                                maxStage = crop.getMaxStage();
                                int growth = (int) (yearProgress * (double) maxStage) + 3 - random.nextInt(2);
                                if (growth > maxStage) {
                                    growth = maxStage;
                                }

                                world.setBlockState(pos, cropBlock.getDefaultState().withProperty(cropBlock.getStageProperty(), growth).withProperty(BlockCropTFC.WILD, true), 2);
                            }
                        }
                    }

                    if (crop == Crop.RICE) {
                        Plant plant = TFCRegistries.PLANTS.getValue(PlantsTFCF.SAWGRASS);
                        BlockTallGrassWater plantBlock = BlockTallGrassWater.get(plant);
                        IBlockState state = plantBlock.getDefaultState();
                        IBlockState water = plant.getWaterType();
                        cropBlock = BlockCropTFC.get(crop);

                        for (maxStage = random.nextInt(Math.round(6.0F / floraDiversity)); (float) maxStage < (2.0F + floraDensity + floraDiversity) * (float) (5 + random.nextInt(15)); ++maxStage) {
                            BlockPos pos = world.getHeight(chunkBlockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
                            if (world.isAirBlock(pos) && plantBlock.canPlaceBlockAt(world, pos) && plant.isValidFloatingWaterDepth(world, pos, water) && plantBlock.canBlockStay(world, pos, state)) {
                                double yearProgress = (double) CalendarTFC.CALENDAR_TIME.getMonthOfYear().ordinal() / 11.0;
                                maxStage = crop.getMaxStage();
                                int growth = (int) (yearProgress * (double) maxStage) + 3 - random.nextInt(2);
                                if (growth > maxStage) {
                                    growth = maxStage;
                                }

                                world.setBlockState(pos, cropBlock.getDefaultState().withProperty(cropBlock.getStageProperty(), growth).withProperty(BlockCropTFC.WILD, true), 2);
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean isValidPosition(World world, BlockPos pos) {
        return world.isAirBlock(pos) && (BlocksTFC.isSoil(world.getBlockState(pos.down())) || BlocksTFCF.isSoil(world.getBlockState(pos.down())));
    }
}
