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

import static su.terrafirmagreg.api.data.Properties.DirectionProp.DIRECTIONAL;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockDirectional extends BaseBlock {

  public BaseBlockDirectional(Settings settings) {
    super(settings);

    setDefaultState(getBlockState().getBaseState()
      .withProperty(DIRECTIONAL, EnumFacing.NORTH));
  }

  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(DIRECTIONAL, rot.rotate(state.getValue(DIRECTIONAL)));
  }

  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirror) {
    return state.withProperty(DIRECTIONAL, mirror.mirror(state.getValue(DIRECTIONAL)));
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return getDefaultState().withProperty(DIRECTIONAL, EnumFacing.getDirectionFromEntityLiving(pos, placer));
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
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, DIRECTIONAL);
  }


}
