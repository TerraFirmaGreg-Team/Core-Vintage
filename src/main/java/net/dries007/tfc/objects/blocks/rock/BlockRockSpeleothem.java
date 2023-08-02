package net.dries007.tfc.objects.blocks.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.rock.util.IRockBlock;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockSpeleothem extends Block implements IRockBlock, IItemSize {
	public static PropertyEnum<EnumSize> SIZE = PropertyEnum.create("size", EnumSize.class);

	private final RockVariant rockVariant;
	private final RockType rockType;
	private final ResourceLocation modelLocation;

	public BlockRockSpeleothem(RockVariant rockVariant, RockType rockType) {
		super(Material.ROCK);

		this.rockVariant = rockVariant;
		this.rockType = rockType;
		this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockVariant);

		var blockRegistryName = String.format("rock/%s/%s", rockVariant, rockType);
		this.setCreativeTab(CreativeTabsTFC.ROCK_STUFFS);
		this.setSoundType(SoundType.STONE);
		this.setHardness(getFinalHardness());
		this.setHarvestLevel("pickaxe", 0);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
		this.setDefaultState(blockState.getBaseState().withProperty(SIZE, EnumSize.MEDIUM));
		//OreDictionaryModule.register(this, rockBlockType.getName(), rockVariant.getName(), rockVariant.getName() + WordUtils.capitalize(rockType.getName()));
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
		return new ItemBlock(this);
	}

	@Nonnull
	@Override
	public Size getSize(@Nonnull ItemStack stack) {
		return Size.SMALL; // Store anywhere
	}

	@Nonnull
	@Override
	public Weight getWeight(@Nonnull ItemStack stack) {
		return Weight.LIGHT; // Stacksize = 32
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, @Nonnull BlockPos pos) {
		return getBearing(worldIn, pos) > 0;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		var size = EnumSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
		worldIn.setBlockState(pos, state.withProperty(SIZE, size));
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		int size = state.getValue(SIZE).strength;
		if (getBearing(worldIn, pos) < size + 1) {
			worldIn.playEvent(2001, pos, Block.getStateId(worldIn.getBlockState(pos)));
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 1 + random.nextInt(3);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return TFCStorage.getRockItem(rockType);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, @Nonnull IBlockState state, EntityPlayer player) {
		return true;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	private int getBearing(IBlockAccess world, BlockPos pos) {
		return Math.max(getStrength(world, pos.down()), getStrength(world, pos.up()));
	}

	private int getStrength(IBlockAccess world, BlockPos pos) {
		var state = world.getBlockState(pos);
		if (state.isFullBlock())
			return 3;

		if (state.getPropertyKeys().contains(SIZE))
			return state.getValue(SIZE).strength;

		return 0;
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(SIZE).aabb;
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
		return getBoundingBox(blockState, worldIn, pos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos blockPos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean canPlaceTorchOnTop(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
		return true;
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SIZE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SIZE).ordinal();
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(SIZE, EnumSize.values()[Math.min(EnumSize.values().length - 1, meta)]);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		var size = EnumSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
		if (isCenter(worldIn, pos))
			size = EnumSize.MEDIUM;
		return state.withProperty(SIZE, size);
	}

	private boolean isCenter(IBlockAccess world, BlockPos pos) {
		return isThis(world, pos.down()) && isThis(world, pos.up());
	}

	private boolean isThis(IBlockAccess world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() instanceof BlockRockSpeleothem;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation,
						"rocktype=" + rockType.getName() + "," +
								"size=" + state.getValue(SIZE));
			}
		});


		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this),
				this.getMetaFromState(this.getBlockState().getBaseState()),
				new ModelResourceLocation(modelLocation,
						"rocktype=" + rockType.getName() + "," +
								"size=big"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + getRockType().getRockCategory().getLocalizedName());
	}

	public enum EnumSize implements IStringSerializable {

		SMALL(0, 2),
		MEDIUM(1, 4),
		BIG(2, 8);

		public final int strength;
		public final AxisAlignedBB aabb;

		EnumSize(int strength, int width) {
			this.strength = strength;

			float pad = (((16 - width) / 2f) / 16F);
			aabb = new AxisAlignedBB(pad, 0F, pad, 1F - pad, 1F, 1F - pad);
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
