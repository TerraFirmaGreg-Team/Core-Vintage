package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
@Optional.Interface(iface = "git.jbredwards.fluidlogged_api.api.block.IFluidloggable", modid = ModIDs.FLUIDLOGGED)
public abstract class BaseBlockLog extends BlockLog implements IBlockEntry, IFluidloggable {


  protected final BlockSettings settings;


  public BaseBlockLog(BlockSettings settings) {

    this.settings = settings;

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();
  }

  @Override
  public boolean isTranslucent(IBlockState state) {
    return this.settings.isTranslucent();
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return this.settings.isPassable();
  }


  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
  }

  @Override
  public boolean getUseNeighborBrightness(IBlockState state) {
    return this.settings.isUseNeighborBrightness() || lightOpacity == 0;
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
  public boolean isOpaqueCube(IBlockState state) {
    return settings != null && settings.isOpaque();
  }

  @Override
  public int getLightOpacity(IBlockState state) {
    return isFullCube(state) ? 255 : 0;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return settings.getFullCube().apply(state);
  }

  @Override
  public boolean isCollidable() {
    return settings.isCollidable();
  }


  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
    return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
  }

  @Override
  public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
    return settings.getSlipperiness().apply(state, world, pos);
  }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return settings.getLightValue().apply(state, world, pos);
  }

  @Override
  public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
    return settings.getMapColor() != null ? settings.getMapColor() : settings.getMaterial().getMaterialMapColor();
  }


  @Optional.Method(modid = ModIDs.FLUIDLOGGED)
  @Override
  public final boolean isFluidloggable(IBlockState state, World world, BlockPos pos) {
    return isWaterloggable(state, world, pos);
  }

  @Optional.Method(modid = ModIDs.FLUIDLOGGED)
  @Override
  public final boolean isFluidValid(IBlockState state, World world, BlockPos pos, Fluid fluid) {
    return isWaterloggable(state, world, pos) && fluid == FluidRegistry.WATER;
  }

  @Optional.Method(modid = ModIDs.FLUIDLOGGED)
  @Override
  public final boolean canFluidFlow(@NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EnumFacing side) {
    return isWaterloggable(state, world, pos) && canWaterFlow(world, pos, state, side);
  }

  /**
   * Whether this block can be water-logged or not.
   */
  public boolean isWaterloggable(IBlockState state, IBlockAccess world, BlockPos pos) {
    return false;
  }

  /**
   * Whether water can flow into/out of this block.
   */
  public boolean canWaterFlow(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
    return state.getBlockFaceShape(world, pos, side) != BlockFaceShape.SOLID;
  }

}
