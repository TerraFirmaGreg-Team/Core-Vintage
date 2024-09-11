package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.base.item.BaseItemSlab;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockSlab extends BlockSlab implements IBlockSettings {

  public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

  protected final Settings settings;

  public BaseBlockSlab(Settings settings) {
    super(settings.getMaterial());

    this.settings = settings;

    getSettings()
            .ignoresProperties(VARIANT)
            .useNeighborBrightness();

    var state = getBlockState().getBaseState();
    if (!isDouble()) {
      state = state.withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
    }
    setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
  }

  @Override
  public String getTranslationKey(int meta) {
    return super.getTranslationKey();
  }

  public abstract boolean isDouble();

  @Override
  public IProperty<?> getVariantProperty() {
    return VARIANT; // why is this not null-tolerable ...
  }

  @Override
  public Comparable<?> getTypeForItem(ItemStack stack) {
    return Variant.DEFAULT;
  }

  @Override
  public boolean getUseNeighborBrightness(IBlockState state) {
    return getSettings().isUseNeighborBrightness();
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

    if (!this.isDouble()) {
      iblockstate = iblockstate.withProperty(BlockSlab.HALF,
              (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
    }

    return iblockstate;
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    int i = 0;

    if (!this.isDouble() && state.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
      i |= 8;
    }

    return i;
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Item.getItemFromBlock(getHalfSlab());
  }

  @Override
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack(getHalfSlab());
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, BlockSlab.HALF, VARIANT);
  }

  public abstract BaseBlockSlab getHalfSlab();

  @Override
  public @Nullable BaseItemSlab getItemBlock() {
    return this.isDouble() ? null : new BaseItemSlab(this.getHalfSlab(), this.getDoubleSlab());
  }

  public abstract BaseBlockSlab getDoubleSlab();

  public enum Variant implements IStringSerializable {
    DEFAULT;

    @Override
    public String getName() {
      return "default";
    }
  }

}
