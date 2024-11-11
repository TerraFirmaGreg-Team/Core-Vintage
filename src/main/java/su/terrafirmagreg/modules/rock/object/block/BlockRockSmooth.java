package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

import static su.terrafirmagreg.api.data.Properties.BoolProp.CAN_FALL;


@SuppressWarnings("deprecation")
public class BlockRockSmooth extends BlockRock {

  public BlockRockSmooth(RockBlockVariant variant, RockType type) {
    super(variant, type);

    getSettings()
      .ignoresProperties(CAN_FALL)
      .fallable(this.getDefaultState().withProperty(CAN_FALL, true),
                variant.getSpecification(),
                BlocksRock.COBBLE.get(type).getDefaultState())
      .oreDict("stoneSmooth")
      .oreDict("stonePolished");

    setDefaultState(blockState.getBaseState()
                              .withProperty(CAN_FALL, false));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(CAN_FALL, meta == 1);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, CAN_FALL);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(CAN_FALL) ? 1 : 0;
  }


}
