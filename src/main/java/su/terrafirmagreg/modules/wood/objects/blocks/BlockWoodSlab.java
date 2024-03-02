package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.CustomStateMap;
import su.terrafirmagreg.api.models.ICustomStateMapper;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariants;
import su.terrafirmagreg.modules.wood.data.BlocksWood;

import java.util.Random;

@Getter
public abstract class BlockWoodSlab extends BlockSlab implements IWoodBlock, IColorfulBlock, ICustomStateMapper {

	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	protected Block block;
	protected Half halfSlab;
	protected Double doubleSlab;

	private BlockWoodSlab(WoodBlockVariant blockVariant, WoodType type) {
		super(Material.WOOD);

		this.blockVariant = blockVariant;
		this.type = type;
		this.useNeighborBrightness = true;
		this.block = BlocksWood.getBlock(WoodBlockVariants.PLANKS, type);

		IBlockState state = blockState.getBaseState();
		if (!isDouble()) state = state.withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
		setLightOpacity(255);
		setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
		setSoundType(SoundType.STONE);
		setHarvestLevel("axe", 0);
	}

	@Override
	public ItemBlock getItemBlock() {
		return this.isDouble() ? null : new ItemSlab(this.halfSlab, this.halfSlab, this.halfSlab.doubleSlab);
	}

	@NotNull
	@Override
	public String getTranslationKey(int meta) {
		return super.getTranslationKey();
	}

	@NotNull
	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT; // why is this not null-tolerable ...
	}

	@NotNull
	@Override
	public Comparable<?> getTypeForItem(@NotNull ItemStack stack) {
		return Variant.DEFAULT;
	}

	@NotNull
	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(BlockSlab.HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
		}

		return iblockstate;
	}

	@Override
	public int getMetaFromState(@NotNull IBlockState state) {
		int i = 0;

		if (!this.isDouble() && state.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
			i |= 8;
		}

		return i;
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getBlockHardness(@NotNull IBlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos) {
		return block.getBlockHardness(blockState, worldIn, pos);
	}

	@NotNull
	@Override
	public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
		return Item.getItemFromBlock(halfSlab);
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getExplosionResistance(@NotNull Entity exploder) {
		return block.getExplosionResistance(exploder);
	}

	@NotNull
	@SuppressWarnings("deprecation")
	@Override
	public ItemStack getItem(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
		return new ItemStack(halfSlab);
	}

	@NotNull
	@Override
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, BlockSlab.HALF, VARIANT);
	}

	@Override
	public IBlockColor getColorHandler() {
		return (s, w, p, i) -> this.getType().getColor();
	}

	@Override
	public IItemColor getItemColorHandler() {
		return (s, i) -> this.getType().getColor();
	}

	@NotNull
	@SuppressWarnings("deprecation")
	@Override
	public SoundType getSoundType() {
		return block.getSoundType();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelLoader.setCustomStateMapper(this,
				new CustomStateMap.Builder()
						.customPath(getResourceLocation())
						.ignore(BlockWoodSlab.VARIANT)
						.build());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelManager.registerBlockInventoryModel(this, getResourceLocation());
	}

	public enum Variant implements IStringSerializable {
		DEFAULT;

		@NotNull
		@Override
		public String getName() {
			return "default";
		}
	}

	public static class Double extends BlockWoodSlab {

		public Double(WoodBlockVariant blockVariant, WoodType type) {
			super(blockVariant, type);

		}

		@Override
		public boolean isDouble() {
			return true;
		}


	}

	public static class Half extends BlockWoodSlab {

		public Half(WoodBlockVariant blockVariant, WoodType type) {
			super(blockVariant, type);

			doubleSlab = (Double) BlocksWood.getBlock(WoodBlockVariants.SLAB_DOUBLE, type);
			doubleSlab.halfSlab = this;
			halfSlab = this;

//            OreDictionaryHelper.register(this, variant.toString());
//            OreDictionaryHelper.register(this, variant.toString(), "wood");
//            OreDictionaryHelper.register(this, variant.toString(), "wood", type.toString());
		}

		@Override
		public boolean isDouble() {
			return false;
		}
	}
}
