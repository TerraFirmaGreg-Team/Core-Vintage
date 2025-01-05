package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.DIRECTIONAL;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockDirectional extends BlockDirectional implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockDirectional(Settings settings) {
    super(settings.getMaterial());

    this.settings = settings;

    setDefaultState(blockState.getBaseState()
      .withProperty(DIRECTIONAL, EnumFacing.NORTH));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(DIRECTIONAL, EnumFacing.byIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(DIRECTIONAL).getIndex();
  }

  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(DIRECTIONAL, rot.rotate(state.getValue(DIRECTIONAL)));
  }

  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    return state.withProperty(DIRECTIONAL, mirrorIn.mirror(state.getValue(DIRECTIONAL)));
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, DIRECTIONAL);
  }

//  @Override
//  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
//    return getDefaultState().withProperty(DIRECTIONAL, EnumFacing.getDirectionFromEntityLiving(pos, placer));
//  }

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
