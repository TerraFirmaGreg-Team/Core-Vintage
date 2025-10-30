package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.STAGE_2;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockSapling extends BlockSapling implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockSapling() {
    this(BlockSettings.of()
      .material(Material.PLANTS));
  }

  public BaseBlockSapling(BlockSettings settings) {
    this.settings = settings;

    getSettings()
      .renderLayer(BlockRenderLayer.CUTOUT)
      .ignoresProperties(STAGE_2, TYPE)
      .nonOpaque()
      .nonFullCube()
      .randomTicks();

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, STAGE_2, TYPE);
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return this.settings.getRenderType();
  }

  @Override
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
  }

  @Override
  public int damageDropped(IBlockState state) {
    return getMetaFromState(state);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(STAGE_2, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(STAGE_2);
  }

  @Override
  public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (state.getValue(STAGE_2) == 0) {
      worldIn.setBlockState(pos, state.cycleProperty(STAGE_2), 4);
    } else {
      this.generateTree(worldIn, pos, state, rand);
    }
  }

  @Override
  public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
    items.add(new ItemStack(this));
  }

  @Override
  public boolean getUseNeighborBrightness(IBlockState state) {
    return this.settings.isUseNeighborBrightness();
  }

  @Override
  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return this.settings.getIsReplaceable().apply(worldIn, pos);
  }

  @Override
  public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return this.settings.isCollidable() ? super.getCollisionBoundingBox(blockState, worldIn, pos) : NULL_AABB;
  }

  @Override
  public String getLocalizedName() {
    return I18n.translateToLocal(this.getTranslationKey() + ".name");
  }
}
