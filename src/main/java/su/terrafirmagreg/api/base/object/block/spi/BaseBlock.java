package su.terrafirmagreg.api.base.object.block.spi;


import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.api.library.VoxelShape;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.Block;
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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;

@Getter
@SuppressWarnings("deprecation")
@Optional.Interface(iface = "git.jbredwards.fluidlogged_api.api.block.IFluidloggable", modid = ModIDs.FLUIDLOGGED)
public abstract class BaseBlock extends Block implements IBlockSettings, IFluidloggable {

  protected final Settings settings;

  public BaseBlock(Settings settings) {
    super(settings.getMaterial(), settings.getMapColor());

    this.settings = settings;

    setResistance(settings.getResistance());
    setHardness(settings.getHardness());
    setSoundType(settings.getSoundType());
    setTickRandomly(settings.isTicksRandomly());
    setHarvestLevel(settings.getHarvestTool(), settings.getHarvestLevel());

    // Fix some potential issues with these fields being set prematurely by the super ctor
    this.fullBlock = getDefaultState().isOpaqueCube();
    this.lightOpacity = this.fullBlock ? 255 : 0;
  }

  public static VoxelShape createShape(double x1, double y1, double z1, double x2, double y2, double z2) {
    return new VoxelShape(x1, y1, z1, x2, y2, z2);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
  }

  @Override
  public boolean getUseNeighborBrightness(IBlockState state) {
    return this.settings.isUseNeighborBrightness();
  }

  @Override
  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return this.settings.isReplaceable() || super.isReplaceable(worldIn, pos);
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
  public boolean isOpaqueCube(IBlockState state) {
    return settings != null && settings.isOpaque();
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return settings.isFullCube();
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
