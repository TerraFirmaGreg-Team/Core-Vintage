package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.data.enums.EnumDefault;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItemSlab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.EnumProp.DEFAULT;

@SuppressWarnings("deprecation")
public abstract class BaseBlockSlab extends BlockSlab implements IBlockEntry {

  @Getter
  protected final BlockSettings settings;
  @Getter
  protected Block modelBlock;
  protected BlockSlab doubleSlab;
  protected BlockSlab halfSlab;

  /**
   * Создает плиту на основе блока
   */
  public BaseBlockSlab(Block model) {
    this(BlockSettings.of(model));

  }

  /**
   * Создает плиту с кастомными настройками
   */
  public BaseBlockSlab(BlockSettings settings) {
    super(settings.getMaterial());
    this.settings = settings;
    this.modelBlock = settings.getBlock();

    getSettings()
      .registryKey(settings.getRegistryKey() + (isDouble() ? "/slab_double" : "/slab"))
      .ignoresProperties(DEFAULT)
      .itemBlock(isDouble() ? null : BaseItemSlab::new)
      .renderLayer(isDouble() ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID)
      .useNeighborBrightness();

    var state = getBlockState().getBaseState();
    if (!isDouble()) {

      state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
    }

    setDefaultState(state.withProperty(DEFAULT, EnumDefault.DEFAULT));
  }


  @Override
  public String getTranslationKey(int meta) {
    return getTranslationKey();
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }

  public BlockSlab getDoubleSlab() {
    return BlockUtils.BLOCK_TO_DOUBLE_SLAB.get(modelBlock);
  }

  public BlockSlab getHalfSlab() {

    return BlockUtils.BLOCK_TO_SLAB.get(modelBlock);
  }

  public static class Half extends BaseBlockSlab {


    public Half(Block model) {
      super(model);
      this.halfSlab = this;

      BlockUtils.BLOCK_TO_SLAB.put(model, this);
    }

    @Override
    public boolean isDouble() {
      return false;
    }
  }

  public static class Double extends BaseBlockSlab {


    public Double(Block model) {
      super(model);
      this.doubleSlab = this;

      BlockUtils.BLOCK_TO_DOUBLE_SLAB.put(model, this);
    }

    @Override
    public boolean isDouble() {
      return true;
    }

  }

  public abstract boolean isDouble();

  @Override
  public IProperty<?> getVariantProperty() {
    return DEFAULT; // why is this not null-tolerable ...
  }

  @Override
  public Comparable<?> getTypeForItem(ItemStack stack) {
    return EnumDefault.DEFAULT;
  }

  @Override
  public boolean getUseNeighborBrightness(IBlockState state) {
    return getSettings().isUseNeighborBrightness();
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    IBlockState iblockstate = this.getDefaultState().withProperty(DEFAULT, EnumDefault.DEFAULT);

    if (!this.isDouble()) {
      iblockstate = iblockstate.withProperty(BlockSlab.HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
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
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
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
    return this.isDouble() ? new BlockStateContainer(this, DEFAULT) : new BlockStateContainer(this, HALF, DEFAULT);
  }


}
