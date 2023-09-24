package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.biomes.BiomeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.*;

import static net.dries007.tfc.api.types.GroundcoverType.STICK;

public class WorldGenTrees implements IWorldGenerator {
    public static void generateLooseSticks(Random rand, int chunkX, int chunkZ, World world, int amount) {
        if (ConfigTFC.General.WORLD.enableLooseSticks) {
            for (int i = 0; i < amount; i++) {
                final int x = chunkX * 16 + rand.nextInt(16) + 8;
                final int z = chunkZ * 16 + rand.nextInt(16) + 8;
                final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

                // Use air, so it doesn't replace other replaceable world gen
                // This matches the check in BlockPlacedItemFlat for if the block can stay
                // Also, only add on soil, since this is called by the world regen handler later
                IBlockState stateDown = world.getBlockState(pos.down());
                if (world.isAirBlock(pos) && stateDown.isSideSolid(world, pos.down(), EnumFacing.UP) && TFCBlocks.isGround(stateDown)) {
                    world.setBlockState(pos, TFCBlocks.getGroundcoverBlock(STICK).getDefaultState());
                }
            }
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!(chunkGenerator instanceof ChunkGenTFC)) return;

        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkDataTFC chunkData = ChunkDataTFC.get(world, chunkBlockPos);
        if (!chunkData.isInitialized()) return;

        final Biome b = world.getBiome(chunkBlockPos);
        if (!(b instanceof BiomeTFC) || b == BiomesTFC.OCEAN || b == BiomesTFC.DEEP_OCEAN)
            return;

        final TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();
        final float diversity = chunkData.getFloraDiversity();
        final float density = chunkData.getFloraDensity();

        List<TreeType> trees = chunkData.getValidTrees();

        Collections.rotate(trees, -(int) (diversity * (trees.size() - 1f)));

        int stickDensity = 3 + (int) (4f * density + 1.5f * trees.size());
        if (trees.isEmpty()) {
            stickDensity = 1 + (int) (1.5f * density);
        }
        generateLooseSticks(random, chunkX, chunkZ, world, (int) (Math.ceil(stickDensity * ConfigTFC.General.WORLD.sticksDensityModifier)));

        // This is to avoid giant regions of no trees whatsoever.
        // It will create sparse trees ( < 1 per chunk) by averaging the climate data to make it more temperate
        // The thought is in very harsh conditions, a few trees might survive outside their typical temperature zone
        if (trees.isEmpty()) {
            if (random.nextFloat() > 0.2f)
                return;

            TreeType extra = chunkData.getSparseGenTree();
            if (extra != null) {
                final int x = chunkX * 16 + random.nextInt(16) + 8;
                final int z = chunkZ * 16 + random.nextInt(16) + 8;
                final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
                extra.makeTree(manager, world, pos, random, true);
            }
            return;
        }

        final int treesPerChunk = (int) (density * 16 - 2);
        final int maxTrees = Math.min(trees.size(), Math.min(5, (int) (1 + (density + diversity) * 2.5f)));
        trees = trees.subList(0, maxTrees);

        int treesPlaced = 0;
        Set<BlockPos> checkedPositions = new HashSet<>();
        for (int i = 0; treesPlaced < treesPerChunk && i < treesPerChunk * 3; i++) {
            BlockPos column = new BlockPos(chunkX * 16 + random.nextInt(16) + 8, 0, chunkZ * 16 + random.nextInt(16) + 8);
            if (!checkedPositions.contains(column)) {
                final BlockPos pos = world.getTopSolidOrLiquidBlock(column);
                final TreeType woodType = getTree(trees, density, random);

                checkedPositions.add(column);
                if (woodType.makeTree(manager, world, pos, random, true)) {
                    treesPlaced++;
                }
            }
        }

        trees.removeIf(t -> !t.hasBushes());
        // TODO кусты от dt
        // Small bushes in high density areas
//        if (density > 0.6f && !trees.isEmpty()) // Density requirement is the same for jungles (kapok trees) to generate
//        {
//            for (int i = 0; i < trees.size() * 4f * density; i++) {
//                final int x = chunkX * 16 + random.nextInt(16) + 8;
//                final int z = chunkZ * 16 + random.nextInt(16) + 8;
//                final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
//                final WoodType woodType = getTree(trees, density, random);
//                ITreeGenerator bushGen = woodType.getBushGenerator();
//                if (bushGen != null && woodType.hasBushes() && bushGen.canGenerateTree(world, pos, woodType)) {
//                    bushGen.generateTree(manager, world, pos, woodType, random, true);
//                }
//            }
//        }
    }

    private TreeType getTree(List<TreeType> trees, float density, Random random) {
        if (trees.size() == 1 || random.nextFloat() < 0.8f - density * 0.4f) {
            return trees.get(0);
        }
        return trees.get(1 + random.nextInt(trees.size() - 1));
    }
}
