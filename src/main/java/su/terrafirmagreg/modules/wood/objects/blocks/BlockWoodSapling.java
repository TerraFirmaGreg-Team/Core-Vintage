package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.client.GrassColorHandler;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.registry.ModelManager;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.objects.itemblocks.ItemBlockWoodSapling;

import java.util.List;
import java.util.Random;

@Getter
public class BlockWoodSapling extends BlockBush implements IWoodBlock, IGrowable, IGrowingPlant, IColorfulBlock {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 4);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);
	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	public BlockWoodSapling(WoodBlockVariant blockVariant, WoodType type) {
		this.blockVariant = blockVariant;
		this.type = type;

		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		setDefaultState(blockState.getBaseState()
				.withProperty(STAGE, 0));

		Blocks.FIRE.setFireInfo(this, 5, 20);

		//OreDictionaryHelper.register(this, variant.toString());
		//OreDictionaryHelper.register(this, variant.toString(), type.toString());
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockWoodSapling(this);
	}


	@SuppressWarnings("deprecation")
	@Override
	@NotNull
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(STAGE);
	}

	@Override
	public void onBlockPlacedBy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer, @NotNull ItemStack stack) {
		TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
		if (te != null) {
			te.resetCounter();
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	@NotNull
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STAGE);
	}

	@Override
	@NotNull
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TETickCounter();
	}


	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
		super.updateTick(world, pos, state, random);

		if (!world.isRemote) {
			TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
			if (te != null) {
				long days = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
//                if (days > this.getTreeVariant().getMinGrowthTime()) {
//                    grow(world, random, pos, state);
//                }
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	@NotNull
	public AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
		return SAPLING_AABB;
	}

	@Override
	@NotNull
	public EnumPlantType getPlantType(@NotNull IBlockAccess world, @NotNull BlockPos pos) {
		return EnumPlantType.Plains;
	}

	@Override
	public boolean canGrow(@NotNull World world, @NotNull BlockPos blockPos, @NotNull IBlockState blockState, boolean b) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(@NotNull World world, @NotNull Random random, @NotNull BlockPos blockPos, @NotNull IBlockState blockState) {
		return false;
	}

	@Override
	public void grow(@NotNull World world, @NotNull Random random, @NotNull BlockPos blockPos, @NotNull IBlockState blockState) {
//        this.getTreeVariant().makeTree(world, blockPos, random, false);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		//this.getTreeVariant().addInfo(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
		return GrowthStatus.GROWING;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerBlockModel(this, new StateMap.Builder().ignore(STAGE).build());
		ModelManager.registerItemModel(Item.getItemFromBlock(this), this.getRegistryName().toString());
	}

	@Override
	public IBlockColor getColorHandler() {
		return GrassColorHandler::computeGrassColor;
	}

	@Override
	public IItemColor getItemColorHandler() {
		return (s, i) -> this.getColorHandler().colorMultiplier(this.getDefaultState(), null, null, i);
	}

}
