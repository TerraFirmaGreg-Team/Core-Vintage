package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.data.Properties.CAN_FALL;


@SuppressWarnings("deprecation")
public class BlockRockSmooth extends BlockRock {

  public BlockRockSmooth(RockBlockVariant variant, RockType type) {
    super(variant, type);

    getSettings()
        .fallable(this.getDefaultState().withProperty(CAN_FALL, true),
            variant.getSpecification(),
            BlocksRock.COBBLE.get(type).getDefaultState())
        .oreDict("stoneSmooth")
        .oreDict("stonePolished");
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(CAN_FALL, meta == 0);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    if (state.getBlock() != this) {
      return 0;
    } else {
      return state.getValue(CAN_FALL) ? 0 : 1;
    }
  }

  @NotNull
  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, CAN_FALL);
  }
}
