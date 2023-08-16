package net.dries007.tfc.world.classic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nonnull;

/**
 * Класс CustomChunkPrimer расширяет класс ChunkPrimer и представляет собой примитивный контейнер для хранения информации о блоках в чанке.
 * Каждый блок в чанке представлен объектом IBlockState.
 */
public class CustomChunkPrimer extends ChunkPrimer {
    private static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();
    private final IBlockState[] data = new IBlockState[65536];

    /**
     * Возвращает состояние блока в указанных координатах (x, y, z).
     *
     * @param x Координата X блока.
     * @param y Координата Y блока.
     * @param z Координата Z блока.
     * @return Состояние блока в указанных координатах.
     */
    @Override
    @Nonnull
    public IBlockState getBlockState(int x, int y, int z) {
        IBlockState iblockstate = data[x << 12 | z << 8 | y];
        return iblockstate == null ? DEFAULT_STATE : iblockstate;
    }

    /**
     * Устанавливает состояние блока в указанных координатах (x, y, z).
     *
     * @param x     Координата X блока.
     * @param y     Координата Y блока.
     * @param z     Координата Z блока.
     * @param state Состояние блока для установки.
     */
    @Override
    public void setBlockState(int x, int y, int z, @Nonnull IBlockState state) {
        data[x << 12 | z << 8 | y] = state;
    }

    /**
     * Находит индекс блока земли на заданных координатах (x, z).
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Индекс блока земли.
     */
    @Override
    public int findGroundBlockIdx(int x, int z) {
        int i = (x << 12 | z << 8) + 256 - 1;

        for (int j = 255; j >= 0; --j) {
            IBlockState iblockstate = data[i + j];
            if (iblockstate != null && iblockstate != DEFAULT_STATE) {
                return j;
            }
        }
        return 0;
    }

    /**
     * Проверяет, является ли блок в указанных координатах (x, y, z) пустым.
     *
     * @param x Координата X блока.
     * @param y Координата Y блока.
     * @param z Координата Z блока.
     * @return true, если блок пустой, иначе false.
     */
    public boolean isEmpty(int x, int y, int z) {
        return data[x << 12 | z << 8 | y] == null;
    }
}
