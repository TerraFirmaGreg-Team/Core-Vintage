package su.terrafirmagreg.api.base.object.block.spi;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockHorizontal extends BaseBlock {


  public BaseBlockHorizontal(Settings settings) {
    super(settings);

    setDefaultState(getBlockState().getBaseState()
      .withProperty(HORIZONTAL, EnumFacing.NORTH));
  }

  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(HORIZONTAL, rot.rotate(state.getValue(HORIZONTAL)));
  }

  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirror) {
    return state.withRotation(mirror.toRotation(state.getValue(HORIZONTAL)));
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing().getOpposite());
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex();
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL);
  }
}
