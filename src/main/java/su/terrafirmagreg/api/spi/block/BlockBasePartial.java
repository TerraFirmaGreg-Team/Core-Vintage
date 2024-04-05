package su.terrafirmagreg.api.spi.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

/**
 * This is a partial block, ie. not a full cube.
 */
public abstract class BlockBasePartial extends BlockBase {

	public BlockBasePartial(Material material) {
		super(material);

	}

	// ---------------------------------------------------------------------------
	// - Rendering
	// ---------------------------------------------------------------------------

	@Override
	@SuppressWarnings("deprecation")
	public boolean isSideSolid(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing side) {

		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullBlock(@NotNull IBlockState state) {

		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(@NotNull IBlockState state) {

		return this.isFullBlock(state);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(@NotNull IBlockState state) {

		return this.isFullBlock(state);
	}

	@Override
	public boolean isNormalCube(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {

		return this.isFullBlock(state);
	}

	@NotNull
	@Override
	@SuppressWarnings("deprecation")
	public BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {

		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean shouldSideBeRendered(@NotNull IBlockState blockState, @NotNull IBlockAccess blockAccess, @NotNull BlockPos pos, @NotNull EnumFacing side) {

		return true;
	}

	@Override
	public boolean doesSideBlockRendering(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing face) {

		return false;
	}
}
