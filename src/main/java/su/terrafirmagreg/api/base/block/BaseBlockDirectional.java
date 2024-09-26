package su.terrafirmagreg.api.base.block;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import lombok.Getter;

import static su.terrafirmagreg.data.Properties.DIRECTIONAL;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockDirectional extends BaseBlock {


  public BaseBlockDirectional(Settings settings) {
    super(settings);

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
//  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer,
//          EnumHand hand) {
//    return getDefaultState().withProperty(DIRECTIONAL, EnumFacing.getDirectionFromEntityLiving(pos, placer));
//  }

}
