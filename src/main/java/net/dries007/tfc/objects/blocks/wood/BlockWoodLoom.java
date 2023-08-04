package net.dries007.tfc.objects.blocks.wood;


import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types2.wood.Wood;
import net.dries007.tfc.api.types2.wood.WoodVariant;
import net.dries007.tfc.api.types2.wood.util.IWoodBlock;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.TELoom;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.minecraft.block.BlockHorizontal.FACING;
import static net.minecraft.block.material.Material.WOOD;

@ParametersAreNonnullByDefault
public class BlockWoodLoom extends BlockContainer implements IItemSize, IWoodBlock {
	protected static final AxisAlignedBB LOOM_EAST_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0625D, 0.5625D, 1.0D, 0.9375D);
	protected static final AxisAlignedBB LOOM_WEST_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.875D, 1.0D, 0.9375D);
	protected static final AxisAlignedBB LOOM_SOUTH_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.125D, 0.9375D, 1.0D, 0.5625D);
	protected static final AxisAlignedBB LOOM_NORTH_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.4375D, 0.9375D, 1.0D, 0.875D);

	private final WoodVariant woodVariant;
	private final Wood wood;
	private final ResourceLocation modelLocation;

	public BlockWoodLoom(WoodVariant woodVariant, Wood wood) {
		super(WOOD, MapColor.AIR);
		this.woodVariant = woodVariant;
		this.wood = wood;
		this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant);

		var blockRegistryName = String.format("wood/%s/%s", woodVariant, wood);
		setRegistryName(MOD_ID, blockRegistryName);
		setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
		setCreativeTab(CreativeTabsTFC.WOOD);
		setSoundType(SoundType.WOOD);
		setHarvestLevel("axe", 0);
		setHardness(0.5f);
		setResistance(3f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public WoodVariant getWoodVariant() {
		return woodVariant;
	}

	@Override
	public Wood getWood() {
		return wood;
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockTFC(this);
	}

	@Nonnull
	@Override
	public Size getSize(@Nonnull ItemStack stack) {
		return Size.LARGE; // Can only store in chests
	}

	@Nonnull
	@Override
	public Weight getWeight(@Nonnull ItemStack stack) {
		return Weight.VERY_HEAVY; // Stacksize = 1
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
		return new TELoom();
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
			case NORTH:
			default:
				return LOOM_NORTH_AABB;
			case SOUTH:
				return LOOM_SOUTH_AABB;
			case WEST:
				return LOOM_WEST_AABB;
			case EAST:
				return LOOM_EAST_AABB;
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TELoom te = Helpers.getTE(worldIn, pos, TELoom.class);
		if (te != null) {
			return te.onRightClick(playerIn);
		}
		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			facing = placer.getHorizontalFacing().getOpposite();
		}
		return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TELoom te = Helpers.getTE(worldIn, pos, TELoom.class);
		if (te != null) {
			te.onBreakBlock(worldIn, pos, state);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation, this.getPropertyString(state.getProperties()));
			}
		});

		for (IBlockState state : this.getBlockState().getValidStates()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
				this.getMetaFromState(state),
				new ModelResourceLocation(modelLocation, "normal"));
		}
	}
}
