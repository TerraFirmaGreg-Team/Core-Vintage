package tfctech.objects.blocks.devices;

import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import tfctech.client.TechGuiHandler;
import tfctech.objects.tileentities.TESmelteryCauldron;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockSmelteryCauldron extends BlockHorizontal implements IItemSize {
	public static final PropertyBool LIT = PropertyBool.create("lit");

	public BlockSmelteryCauldron() {
		super(Material.IRON);
		setHardness(3.0F);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 0);
		setDefaultState(getBlockState().getBaseState().withProperty(LIT, false).withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
		           .withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4))
		           .withProperty(LIT, meta / 4 % 2 != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex()
				+ (state.getValue(LIT) ? 4 : 0);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}

	@Override
	@Nonnull
	@SuppressWarnings("deprecation")
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.UP ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TESmelteryCauldron te = Helpers.getTE(worldIn, pos, TESmelteryCauldron.class);
		if (te != null) {
			te.onBreakBlock(worldIn, pos, state);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
			if (!world.isRemote) {
				if (world.getBlockState(pos.down()).getBlock() instanceof BlockSmelteryFirebox) {
					TESmelteryCauldron smeltery = Helpers.getTE(world, pos, TESmelteryCauldron.class);
					ItemStack held = player.getHeldItem(hand);
					if (held.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
						IFluidHandler fluidHandler = smeltery.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
						if (fluidHandler != null) {
							if (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) {
								held = player.getHeldItem(hand); // Forge update item in hand
								IItemHeat cap = held.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
								if (cap != null) {
									cap.setTemperature(smeltery.getTemp());
								}
							}
						}
					} else {
						TechGuiHandler.openGui(world, pos, player, TechGuiHandler.Type.SMELTERY_CAULDRON);
					}
				} else {
					player.sendStatusMessage(new TextComponentTranslation("tooltip.tfctech.smeltery.invalid"), true);
				}
			}
			return true;
		}
		return false;
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LIT, FACING);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TESmelteryCauldron();
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Nonnull
	@Override
	public Size getSize(@Nonnull ItemStack itemStack) {
		return Size.LARGE;
	}

	@Nonnull
	@Override
	public Weight getWeight(@Nonnull ItemStack itemStack) {
		return Weight.MEDIUM;
	}
}
