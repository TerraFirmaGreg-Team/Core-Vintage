package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockBush extends BlockBush implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockBush(BlockSettings settings) {
    super(settings.getMaterial(), settings.getMapColor());

    this.settings = settings;
    getSettings()
      .renderLayer(BlockRenderLayer.CUTOUT)
      .nonOpaque()
      .nonFullCube()
      .randomTicks();

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();
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


}
