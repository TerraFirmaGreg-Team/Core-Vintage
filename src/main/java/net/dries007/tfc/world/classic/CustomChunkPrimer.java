package net.dries007.tfc.world.classic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

import org.jetbrains.annotations.NotNull;

public class CustomChunkPrimer extends ChunkPrimer {

    private static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();
    private final IBlockState[] data = new IBlockState[65536];

    @Override
    @NotNull
    public IBlockState getBlockState(int x, int y, int z) {
        IBlockState iblockstate = data[x << 12 | z << 8 | y];
        return iblockstate == null ? DEFAULT_STATE : iblockstate;
    }

    @Override
    public void setBlockState(int x, int y, int z, @NotNull IBlockState state) {
        data[x << 12 | z << 8 | y] = state;
    }

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

    public boolean isEmpty(int x, int y, int z) {
        return data[x << 12 | z << 8 | y] == null;
    }
}
