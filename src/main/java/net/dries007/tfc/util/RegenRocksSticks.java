package net.dries007.tfc.util;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.world.ConfigWorld;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.classic.objects.generator.groundcover.GeneratorSurfaceRocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;


import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;

import java.util.Random;

public class RegenRocksSticks extends GeneratorSurfaceRocks {

    private static Boolean isReplaceable(World world, BlockPos pos) {
        //Modified to allow replacement of grass during spring regen
        Block test = world.getBlockState(pos).getBlock();
        return test instanceof BlockShortGrassTFC || test.isAir(world.getBlockState(pos), world, pos);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0) {
            final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
            final var baseChunkData = CapabilityChunkData.get(world, chunkBlockPos);

            // Get the proper list of veins
            int xoff = chunkX * 16 + 8;
            int zoff = chunkZ * 16 + 8;

            for (int i = 0; i < ConfigWorld.MISC.looseRocksFrequency; i++) {
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
