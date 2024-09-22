package su.terrafirmagreg.api.base.block;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import lombok.Getter;

import static su.terrafirmagreg.data.Properties.HORIZONTAL;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockHorizontal extends BaseBlock {


  public BaseBlockHorizontal(Settings settings) {
    super(settings);

    setDefaultState(blockState.getBaseState()
            .withProperty(HORIZONTAL, EnumFacing.NORTH));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    EnumFacing enumfacing = EnumFacing.byIndex(meta);

    if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH;
    }

    return this.getDefaultState().withProperty(HORIZONTAL, enumfacing);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getIndex();
  }

  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(HORIZONTAL, rot.rotate(state.getValue(HORIZONTAL)));
  }

  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    return state.withProperty(HORIZONTAL, mirrorIn.mirror(state.getValue(HORIZONTAL)));
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL);
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer,
          EnumHand hand) {
    return getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing().getOpposite());
  }
}
