package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Класс WorldGenFalls реализует интерфейс IWorldGenerator и используется для генерации водопадов в мире.
 * Он размещает блоки водопада на случайных координатах в заданном чанке.
 */
public class WorldGenFalls implements IWorldGenerator {
    private final IBlockState block;
    private final int rarity;

    /**
     * Конструктор класса WorldGenFalls.
     *
     * @param blockIn Блок, представляющий водопад.
     * @param rarity  Частота генерации водопадов.
     */
    public WorldGenFalls(IBlockState blockIn, int rarity) {
        this.block = blockIn;
        this.rarity = rarity;
    }

    /**
     * Генерирует водопады в мире.
     *
     * @param random         Генератор случайных чисел.
     * @param chunkX         Координата X чанка.
     * @param chunkZ         Координата Z чанка.
     * @param world          Мир, в котором происходит генерация.
     * @param chunkGenerator Генератор чанков.
     * @param chunkProvider  Поставщик чанков.
     */
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        for (int k5 = 0; k5 < rarity; ++k5) {
            int x = random.nextInt(16) + 8;
            int z = random.nextInt(16) + 8;
            int y = random.nextInt(WorldTypeTFC.SEALEVEL - 50) + 30;
            BlockPos pos = new BlockPos(chunkX << 4, y, chunkZ << 4).add(x, 0, z);
            if (!TFCBlocks.isRawStone(world.getBlockState(pos.down())) && !TFCBlocks.isRawStone(world.getBlockState(pos.up())) && (!TFCBlocks.isRawStone(world.getBlockState(pos)) || !world.isAirBlock(pos))) {
                continue;
            }
            int rawHorizontal = 0, airHorizontal = 0;
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                if (world.isAirBlock(pos.offset(facing))) {
                    airHorizontal++;
                } else if (TFCBlocks.isRawStone(world.getBlockState(pos.offset(facing)))) {
                    rawHorizontal++;
                }
                if (airHorizontal > 1) break;
            }
            if (rawHorizontal == 3 && airHorizontal == 1) {
                world.setBlockState(pos, block, 2);
                world.immediateBlockTick(pos, block, random);
            }
        }
    }
}
