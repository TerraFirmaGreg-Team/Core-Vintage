package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockRenderLayer;


import static su.terrafirmagreg.data.Properties.MOSSY;

public class BlockRockBricks extends BlockRock {

  public BlockRockBricks(RockBlockVariant variant, RockType type) {
    super(variant, type);

    getSettings()
            .renderLayer(BlockRenderLayer.CUTOUT)
            .oreDict("stoneBrick")
            .oreDict("brickStone");

    setDefaultState(blockState.getBaseState()
            .withProperty(MOSSY, false));
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, MOSSY);
  }
}
