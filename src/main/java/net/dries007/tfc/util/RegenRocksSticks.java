package net.dries007.tfc.util;


import net.dries007.tfc.module.plant.common.blocks.BlockPlantShortGrass;
import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.worldgen.WorldGenLooseRocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

public class RegenRocksSticks extends WorldGenLooseRocks {
    private static Boolean isReplaceable(World world, BlockPos pos) {
        // Modified to allow replacement of grass during spring regen
        var test = world.getBlockState(pos).getBlock();
        return test instanceof BlockPlantShortGrass || test.isAir(world.getBlockState(pos), world, pos);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkGenerator instanceof ChunkGenTFC && world.provider.getDimension() == 0) {
            final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
            final ChunkDataTFC baseChunkData = ChunkDataTFC.get(world, chunkBlockPos);

            // Get the proper list of veins
            int xoff = chunkX * 16 + 8;
            int zoff = chunkZ * 16 + 8;

            for (int i = 0; i < ConfigTFC.General.WORLD.looseRocksFrequency; i++) {
                BlockPos pos = new BlockPos(xoff + random.nextInt(16), 0, zoff + random.nextInt(16));
                var rock = baseChunkData.getRock1(pos);
                generateRock(random, world, pos.up(world.getTopSolidOrLiquidBlock(pos).getY()), rock);
            }
        }
    }

    @Override
    protected void generateRock(Random random, World world, BlockPos pos, RockType type) {
        if (isReplaceable(world, pos)) {
            super.generateRock(random, world, pos, type);
        }
    }
}
