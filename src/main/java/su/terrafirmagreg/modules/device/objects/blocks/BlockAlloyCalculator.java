package su.terrafirmagreg.modules.device.objects.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.objects.tiles.TEAlloyCalculator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static su.terrafirmagreg.api.models.Blockstates.HORIZONTAL;

public class BlockAlloyCalculator extends BlockBase implements ITEBlock {

	private static final AxisAlignedBB BOUNDS_NS = new AxisAlignedBB(3 / 16d, 0, 5 / 16d, 13 / 16d, 4 / 16d, 11 / 16d);
	private static final AxisAlignedBB BOUNDS_WE = new AxisAlignedBB(5 / 16d, 0, 3 / 16d, 11 / 16d, 4 / 16d, 13 / 16d);

	public BlockAlloyCalculator() {
		super(Material.IRON);

		setDefaultState(this.blockState.getBaseState()
				.withProperty(HORIZONTAL, EnumFacing.NORTH));
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HORIZONTAL);
	}

	@Override
	public boolean onBlockActivated(@Nonnull World worldIn, @NotNull BlockPos pos, @Nonnull IBlockState state, @NotNull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
		GuiHandler.openGui(worldIn, pos, playerIn, GuiHandler.Type.ALLOY_CALCULATOR);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, @Nonnull BlockPos pos, IBlockState state, EntityLivingBase placer, @Nonnull ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(HORIZONTAL, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Nonnull
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(HORIZONTAL, enumfacing);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(HORIZONTAL).getIndex();
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(HORIZONTAL, rot.rotate(state.getValue(HORIZONTAL)));
	}

	@Nonnull
	@SuppressWarnings("deprecation")
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(HORIZONTAL)));
	}

	@SuppressWarnings("deprecation")
	public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(@NotNull IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(@NotNull IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull AxisAlignedBB getBoundingBox(IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
		return state.getValue(HORIZONTAL).getAxis() == EnumFacing.Axis.Z ? BOUNDS_NS : BOUNDS_WE;
	}

	@Override
	public boolean hasTileEntity(@NotNull IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
		return new TEAlloyCalculator();
	}

	@Override
	public @NotNull String getName() {
		return "device/alloy_calculator";
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TEAlloyCalculator.class;
	}
}
