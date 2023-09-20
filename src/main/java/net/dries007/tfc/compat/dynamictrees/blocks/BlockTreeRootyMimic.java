package net.dries007.tfc.compat.dynamictrees.blocks;

import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.blocks.BlockRootyDirt;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.soil.api.variant.block.ISoilBlock;
import net.dries007.tfc.module.soil.common.SoilStorage;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.module.soil.api.type.SoilTypes.LOAM;
import static net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariants.DIRT;

@ParametersAreNonnullByDefault
public class BlockTreeRootyMimic extends BlockRootyDirt implements IItemProvider, IHasModel {
    private static final EnumFacing[] NOT_UP = new EnumFacing[]{
            EnumFacing.DOWN,
            EnumFacing.EAST,
            EnumFacing.NORTH,
            EnumFacing.WEST,
            EnumFacing.SOUTH
    };

    public BlockTreeRootyMimic() {
        super(false);
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return null;
    }

    /**
     * Возвращает состояние блока-модели.
     *
     * @param access доступ к блокам
     * @param pos    позиция блока
     * @return состояние блока-модели
     */
    @Override
    public IBlockState getMimic(IBlockAccess access, BlockPos pos) {
        var mimicState = super.getMimic(access, pos);
        if (mimicState.getBlock() == Blocks.DIRT) {
            // Ищем вручную
            for (int i = 1; i < 4; i++) {
                for (EnumFacing d : NOT_UP) {
                    var state = access.getBlockState(pos.offset(d, i));
                    if (state.getBlock() instanceof ISoilBlock) {
                        var soil = ((ISoilBlock) state.getBlock()).getType();
                        return SoilStorage.getSoilBlock(DIRT, soil).getDefaultState();
                    }
                }
            }
            // Если вокруг нет блоков почвы, возвращаем состояние блока почвы по умолчанию
            return SoilStorage.getSoilBlock(DIRT, LOAM).getDefaultState();
        }
        return mimicState;
    }

    /**
     * Получает список предметов, которые выпадают при разрушении блока.
     *
     * @param drops   список предметов
     * @param world   доступ к блокам
     * @param pos     позиция блока
     * @param state   состояние блока
     * @param fortune уровень фортуны
     */
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        // Очищаем список предметов
        drops.clear();
        // Добавляем предмет в список
        drops.add(new ItemStack(getDecayBlockState(world, pos).getBlock()));
    }

    /**
     * Возвращает состояние блока распада.
     *
     * @param world доступ к блокам
     * @param pos   позиция блока
     * @return состояние блока распада
     */
    @Override
    public IBlockState getDecayBlockState(IBlockAccess world, BlockPos pos) {
        if (world instanceof World) {
            var chunkData = ((World) world).getChunk(pos).getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
            if (chunkData != null) {
                var soil = chunkData.getSoilHeight(pos);
                return SoilStorage.getSoilBlock(DIRT, soil).getDefaultState();
            }
        }
        return super.getDecayBlockState(world, pos);
    }

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder()
                .ignore(BlockRooty.LIFE)
                .build());
    }
}
