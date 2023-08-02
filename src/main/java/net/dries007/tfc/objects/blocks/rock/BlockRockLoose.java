package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.rock.util.IRockBlock;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
public class BlockRockLoose extends Block implements IRockBlock {

	private static final AxisAlignedBB STONE_AABB = new AxisAlignedBB(2.0 / 16.0, 0.0 / 16.0, 2.0 / 16.0, 14.0 / 16.0, 2.0 / 16.0, 14.0 / 16.0);

	public static final PropertyDirection AXIS = PropertyDirection.create("axis", EnumFacing.Plane.HORIZONTAL);

	private final RockVariant rockVariant;
	private final RockType rockType;
	private final ResourceLocation modelLocation;

	public BlockRockLoose(RockVariant rockVariant, RockType rockType) {
		super(Material.ROCK);

		this.rockVariant = rockVariant;
		this.rockType = rockType;
		this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockVariant);

		var blockRegistryName = String.format("rock/%s/%s", rockVariant, rockType);
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.1f);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

		setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.NORTH));
	}

	@Nonnull
	@Override
	public RockVariant getRockVariant() {
		return rockVariant;
	}

	@Nonnull
	@Override
	public RockType getRockType() {
		return rockType;
	}

	@Override
	public ItemBlock getItemBlock() {
		return null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP)) {
			worldIn.destroyBlock(pos, false);
		}
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		var itemStack = new ItemStack(TFCStorage.ITEMROCK_MAP.get(rockType));

		if (playerIn.addItemStackToInventory(itemStack)) {
			worldIn.setBlockToAir(pos);

			playerIn.swingArm(EnumHand.MAIN_HAND);
			playerIn.playSound(SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, 1.0f, 1.0f);
		}

		return true;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(TFCStorage.ITEMROCK_MAP.get(rockType)));
	}

	@Nonnull
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(TFCStorage.ITEMROCK_MAP.get(rockType));
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
		return STONE_AABB;
	}

	@Override
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Nullable
	@Override
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return null;
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		IBlockState stateDown = worldIn.getBlockState(pos.down());

		return stateDown.isSideSolid(worldIn, pos, EnumFacing.DOWN) && state.getBlock().equals(Blocks.AIR);
	}

	@Override
	@Nonnull
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation,
						"axis=" + state.getValue(AXIS) + "," +
								"rocktype=" + rockType.getName());
			}
		});
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AXIS, EnumFacing.byHorizontalIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AXIS).getHorizontalIndex();
	}

	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AXIS);
	}
}
