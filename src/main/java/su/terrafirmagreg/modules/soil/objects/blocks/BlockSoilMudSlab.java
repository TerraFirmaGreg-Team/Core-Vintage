package su.terrafirmagreg.modules.soil.objects.blocks;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.ICustomStateMapper;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants;
import su.terrafirmagreg.modules.soil.data.BlocksSoil;

import java.util.Random;

@Getter
public abstract class BlockSoilMudSlab extends BlockSlab implements ISoilBlockVariant, ICustomStateMapper {

	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

	private final SoilBlockVariant blockVariant;
	private final SoilType type;

	protected Block block;
	protected Half halfSlab;
	protected Double doubleSlab;

	private BlockSoilMudSlab(SoilBlockVariant blockVariant, SoilType type) {
		super(Material.ROCK);

		this.blockVariant = blockVariant;
		this.type = type;
		this.useNeighborBrightness = true;
		this.block = BlocksSoil.getBlock(SoilBlockVariants.MUD_BRICKS, type);

		var state = blockState.getBaseState();
		if (!isDouble()) state = state.withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
		setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
		setSoundType(SoundType.GROUND);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public ItemBlock getItemBlock() {
		return this.isDouble() ? null : new ItemSlab(this.block, this.halfSlab, this.halfSlab.doubleSlab);
	}

	@Override
	@NotNull
	public String getTranslationKey(int meta) {
		return super.getTranslationKey();
	}

	@Override
	@NotNull
	public IProperty<?> getVariantProperty() {
		return VARIANT; // why is this not null-tolerable ...
	}

	@Override
	@NotNull
	public Comparable<?> getTypeForItem(@NotNull ItemStack stack) {
		return Variant.DEFAULT;
	}

	@SuppressWarnings("deprecation")
	@Override
	@NotNull
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(BlockSlab.HALF,
					(meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
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

	@Override
	@NotNull
	public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
		return Item.getItemFromBlock(halfSlab);
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getExplosionResistance(@NotNull Entity exploder) {
		return block.getExplosionResistance(exploder);
	}

	@SuppressWarnings("deprecation")
	@Override
	@NotNull
	public ItemStack getItem(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
		return new ItemStack(halfSlab);
	}

	@Override
	@NotNull
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, BlockSlab.HALF, VARIANT);
	}

	@SuppressWarnings("deprecation")
	@NotNull
	@Override
	public SoundType getSoundType() {
		return block.getSoundType();
	}

	@NotNull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerStateMapper(this, new StateMap.Builder().ignore(BlockSoilMudSlab.VARIANT).build());
	}


	public enum Variant implements IStringSerializable {
		DEFAULT;

		@Override
		@NotNull
		public String getName() {
			return "default";
		}
	}

	public static class Double extends BlockSoilMudSlab {

		public Double(SoilBlockVariant variant, SoilType type) {
			super(variant, type);
		}

		@Override
		public boolean isDouble() {
			return true;
		}
	}

	public static class Half extends BlockSoilMudSlab {

		public Half(SoilBlockVariant variant, SoilType type) {
			super(variant, type);

			this.doubleSlab = (Double) BlocksSoil.getBlock(SoilBlockVariants.SLAB_DOUBLE_MUD_BRICKS, type);
			this.doubleSlab.halfSlab = this;
			this.halfSlab = this;

			//OreDictionaryHelper.register(this, variant.toString());
			//OreDictionaryHelper.register(this, variant.toString(), type.toString());
		}

		@Override
		public boolean isDouble() {
			return false;
		}

	}
}
