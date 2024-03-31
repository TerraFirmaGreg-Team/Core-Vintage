package su.terrafirmagreg.api.spi.block;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.model.ICustomStateMapper;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.util.ModelUtils;

@Getter
public abstract class BlockSlabBase extends BlockSlab implements IAutoReg, ICustomStateMapper {

	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

	protected Block block;
	protected BlockSlab halfSlab;
	protected BlockSlab doubleSlab;

	public BlockSlabBase(Material material) {
		super(material);

		this.useNeighborBrightness = true;

		var state = blockState.getBaseState();
		if (!isDouble()) state = state.withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
		setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
		setLightOpacity(255);
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

	@NotNull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelUtils.registerStateMapper(this, new CustomStateMap.Builder().ignore(VARIANT).build());
	}

	public enum Variant implements IStringSerializable {
		DEFAULT;

		@Override
		@NotNull
		public String getName() {
			return "default";
		}
	}
}
