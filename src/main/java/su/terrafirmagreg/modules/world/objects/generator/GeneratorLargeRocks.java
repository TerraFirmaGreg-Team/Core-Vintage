package su.terrafirmagreg.modules.world.objects.generator;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


import java.util.Random;

public class GeneratorLargeRocks implements IWorldGenerator {

    @Override
    public void generate(Random rng, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        BlockPos start = world.getTopSolidOrLiquidBlock(chunkBlockPos.add(8 + rng.nextInt(16), 0, 8 + rng.nextInt(16))).add(0, -1, 0);
        if (start.getY() > 155 && !BlockUtils.isSoil(world.getBlockState(start))) return;

        int y = 1;
        boolean isFlatEnough = false;
        outer:
        while (y-- > -2 && !isFlatEnough) {
            if (!world.getBlockState(start.add(0, y, 0)).isBlockNormalCube()) continue;

            for (int x = -6; x <= 6; x++) {
                for (int z = -6; z <= 6; z++) {
                    if (!world.getBlockState(start.add(x, y, z)).isBlockNormalCube())
                        continue outer;
                }
            }
            isFlatEnough = true;
        }

        if (!isFlatEnough) return;

        genFromPoint(world, rng, start.add(0, y, 0));
        if (rng.nextInt(1) == 0) {
            genFromPoint(world, rng,
                    start.add(
                            (rng.nextInt(2) + 1) * (rng.nextBoolean() ? 1 : -1),
                            y + (rng.nextInt(2) + 1) * (rng.nextBoolean() ? 1 : -1),
                            (rng.nextInt(2) + 1) * (rng.nextBoolean() ? 1 : -1)));
        }
    }

    private void genFromPoint(World world, Random rng, BlockPos start) {
        var rock = ProviderChunkData.getRockHeight(world, start);
        final int size = rng.nextInt(10) == 0 ? 4 : 3;
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                for (int y = -2; y <= 2; y++) {
                    if (x * x + z * z + y * y > size * size) continue;
                    world.setBlockState(start.add(x, y, z), BlocksRock.RAW.get(rock).getDefaultState());
                }
            }
        }
    }
}
