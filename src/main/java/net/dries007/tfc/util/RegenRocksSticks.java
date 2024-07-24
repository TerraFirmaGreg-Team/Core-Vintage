package net.dries007.tfc.util;

import su.terrafirmagreg.modules.world.ConfigWorld;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.objects.generator.GeneratorLooseRocks;
import su.terrafirmagreg.modules.world.objects.generator.vein.Vein;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;


import com.google.common.collect.Sets;
import net.dries007.tfc.api.capability.chunkdata.ChunkData;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

public class RegenRocksSticks extends GeneratorLooseRocks {

    public RegenRocksSticks(boolean generateOres) {
        super(generateOres);
    }

    private static Boolean isReplaceable(World world, BlockPos pos) {
        //Modified to allow replacement of grass during spring regen
        Block test = world.getBlockState(pos).getBlock();
        return test instanceof BlockShortGrassTFC || test.isAir(world.getBlockState(pos), world, pos);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0) {
            final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
            final ChunkData baseChunkData = ChunkData.get(world, chunkBlockPos);

            // Get the proper list of veins
            Set<Vein> veins = Sets.newHashSet();
            int xoff = chunkX * 16 + 8;
            int zoff = chunkZ * 16 + 8;

            if (generateOres) {
                // Grab 2x2 area
                ChunkData[] chunkData = {
                        baseChunkData, // This chunk
                        ChunkData.get(world, chunkBlockPos.add(16, 0, 0)),
                        ChunkData.get(world, chunkBlockPos.add(0, 0, 16)),
                        ChunkData.get(world, chunkBlockPos.add(16, 0, 16))
                };
                if (!chunkData[0].isInitialized()) return;

                // Default to 35 below the surface, like classic
                int lowestYScan = Math.max(10, world.getTopSolidOrLiquidBlock(chunkBlockPos)
                        .getY() - ConfigWorld.MISC.looseRockScan);

                for (ChunkData data : chunkData) {
                    veins.addAll(data.getGeneratedVeins());
                }

                if (!veins.isEmpty()) {
                    veins.removeIf(v -> v.getType() == null || !v.getType()
                            .hasLooseRocks() || v.getHighestY() < lowestYScan);
                }
            }

            for (int i = 0; i < ConfigWorld.MISC.looseRocksFrequency * factor; i++) {
                BlockPos pos = new BlockPos(xoff + random.nextInt(16), 0, zoff + random.nextInt(16));
                Rock rock = baseChunkData.getRock1(pos);
                generateRock(world, pos.up(world.getTopSolidOrLiquidBlock(pos)
                        .getY()), getRandomVein(Arrays.asList(veins.toArray(new Vein[0])), pos, random), rock);
            }
        }
    }

    /*@Nullable
    private Vein getRandomVein(Set<Vein> veins, BlockPos pos, Random rand)
    {
        if (!veins.isEmpty() && rand.nextDouble() < 0.4)
        {
            Optional<Vein> vein = veins.stream().findAny();
            if (!veins.isEmpty())
            {
                Vein veintarget = vein.get();
                if (veintarget.inRange(pos.getX(), pos.getZ(), 8))
                {
                    return veintarget;
                }
            }
        }
        return null;
    }*/

    @Override
    protected void generateRock(World world, BlockPos pos, @Nullable Vein vein, Rock rock) {
        if (isReplaceable(world, pos)) {
            super.generateRock(world, pos, vein, rock);
        }
    }
}
