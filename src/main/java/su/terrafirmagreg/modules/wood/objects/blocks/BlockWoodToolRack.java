package su.terrafirmagreg.modules.wood.objects.blocks;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.client.render.TESRWoodToolRack;
import su.terrafirmagreg.modules.wood.objects.tiles.TEWoodToolRack;

import static net.minecraft.block.BlockHorizontal.FACING;
import static net.minecraft.util.EnumFacing.*;


public class BlockWoodToolRack extends BlockWood implements ITEBlock {

	protected static final AxisAlignedBB RACK_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
	protected static final AxisAlignedBB RACK_WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB RACK_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
	protected static final AxisAlignedBB RACK_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);


	public BlockWoodToolRack(WoodBlockVariant blockVariant, WoodType type) {
		super(blockVariant, type);

		setHarvestLevel("axe", 0);
		setHardness(0.5f);
		setResistance(3f);
		setDefaultState(this.blockState.getBaseState()
				.withProperty(FACING, NORTH));
	}

	@NotNull
	@Override
	public Size getSize(@NotNull ItemStack stack) {
		return Size.LARGE;
	}

	@NotNull
	@Override
	public Weight getWeight(@NotNull ItemStack stack) {
		return Weight.VERY_HEAVY;
	}

	@Override
	@SuppressWarnings("deprecation")
	@NotNull
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, byHorizontalIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(@NotNull IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	@NotNull
	public EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	@SuppressWarnings("deprecation")
	@NotNull
	public AxisAlignedBB getBoundingBox(IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
		return switch (state.getValue(FACING)) {
			default -> RACK_NORTH_AABB;
			case SOUTH -> RACK_SOUTH_AABB;
			case WEST -> RACK_WEST_AABB;
			case EAST -> RACK_EAST_AABB;
		};
	}

	@Override
	@SuppressWarnings("deprecation")
	@NotNull
	public BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(@NotNull IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		if (!Helpers.canHangAt(worldIn, pos, state.getValue(FACING))) {
			dropBlockAsItem(worldIn, pos, state, 0);
			var te = Helpers.getTE(worldIn, pos, TEWoodToolRack.class);
			if (te != null) {
				te.onBreakBlock();
			}
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
		var te = Helpers.getTE(worldIn, pos, TEWoodToolRack.class);
		if (te != null) {
			te.onBreakBlock();
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean canPlaceBlockAt(@NotNull World worldIn, @NotNull BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && Helpers.getASolidFacing(worldIn, pos, null, HORIZONTALS) != null;
	}

	public boolean onBlockActivated(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			var te = Helpers.getTE(worldIn, pos, TEWoodToolRack.class);
			if (te != null) {
				return te.onRightClick(playerIn, hand, getSlotFromPos(state, hitX, hitY, hitZ));
			}
		}
		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	@NotNull
	public IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer) {
		if (facing.getAxis() == Axis.Y) {
			facing = placer.getHorizontalFacing().getOpposite();
		}
		return this.getDefaultState().withProperty(FACING, Helpers.getASolidFacing(worldIn, pos, facing, HORIZONTALS));
	}

	@Override
	@NotNull
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public boolean hasTileEntity(@NotNull IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
		return new TEWoodToolRack();
	}

	@Override
	@NotNull
	@SuppressWarnings("ConstantConditions")
	public ItemStack getPickBlock(@NotNull IBlockState state, @Nullable RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, @NotNull EntityPlayer player) {
		if (target != null) {
			var vec = target.hitVec.subtract(pos.getX(), pos.getY(), pos.getZ());
			var te = Helpers.getTE(world, pos, TEWoodToolRack.class);
			if (te != null) {
				ItemStack item = te.getItems().get(getSlotFromPos(state, (float) vec.x, (float) vec.y, (float) vec.z));
				if (!item.isEmpty()) {
					return item;
				}
			}
		}
		return super.getPickBlock(state, target, world, pos, player);
	}

	public int getSlotFromPos(IBlockState state, float x, float y, float z) {
		int slot = 0;
		if ((state.getValue(FACING).getAxis().equals(Axis.Z) ? x : z) > .5f) {
			slot += 1;
		}
		if (y < 0.5f) {
			slot += 2;
		}
		return slot;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TEWoodToolRack.class;
	}

	@Override
	public TileEntitySpecialRenderer<?> getTileRenderer() {
		return new TESRWoodToolRack();
	}
}
