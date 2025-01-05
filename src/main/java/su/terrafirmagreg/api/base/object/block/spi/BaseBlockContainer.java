package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderTile;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Arrays;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockContainer extends BlockContainer implements IProviderTile, IBlockSettings {

  protected final Settings settings;

  public BaseBlockContainer(Settings settings) {
    super(settings.getMaterial(), settings.getMapColor());

    this.settings = settings;
  }

  @Override
  protected boolean hasInvalidNeighbor(World world, BlockPos pos) {
    return Arrays.stream(EnumFacing.HORIZONTALS).anyMatch(facing -> this.isInvalidNeighbor(world, pos, facing));
  }

  @Override
  public boolean getUseNeighborBrightness(IBlockState state) {
    return !this.settings.isUseNeighborBrightness() || super.getUseNeighborBrightness(state);
  }

  @Override
  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return this.settings.isReplaceable() || super.isReplaceable(worldIn, pos);
  }

  @Override
  public boolean getTickRandomly() {
    return this.settings.isTicksRandomly();
  }

  @Override
  public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return this.settings.isCollidable() ? super.getCollisionBoundingBox(blockState, worldIn, pos) : NULL_AABB;
  }


  @Override
  public String getTranslationKey() {
    return ModUtils.localize("block", this.getRegistryName());
  }

  @Override
  public String getHarvestTool(IBlockState state) {
    return this.settings.getHarvestTool();
  }

  @Override
  public int getHarvestLevel(IBlockState state) {
    return this.settings.getHarvestLevel();
  }
}
