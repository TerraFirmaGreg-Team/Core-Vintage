package net.dries007.tfc.compat.dynamictrees.blocks;

import com.ferreusveritas.dynamictrees.blocks.BlockRootyDirt;
import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.api.types.soil.type.SoilTypes.LOAM;
import static net.dries007.tfc.api.types.soil.variant.SoilBlockVariants.DIRT;

@ParametersAreNonnullByDefault
public class BlockRootyDirtTFC extends BlockRootyDirt {
	private static final EnumFacing[] NOT_UP = new EnumFacing[]{EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.SOUTH};

	public BlockRootyDirtTFC() {
		super(false);
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
						return TFCBlocks.getSoilBlock(DIRT, soil).getDefaultState();
					}
				}
			}
			// Если вокруг нет блоков почвы, возвращаем состояние блока почвы по умолчанию
			return TFCBlocks.getSoilBlock(DIRT, LOAM).getDefaultState();
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
			ChunkDataTFC chunkData = ((World) world).getChunk(pos).getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
			if (chunkData != null) {
				var soil = chunkData.getSoilHeight(pos);
				return TFCBlocks.getSoilBlock(DIRT, soil).getDefaultState();
			}
		}
		return super.getDecayBlockState(world, pos);
	}
}
