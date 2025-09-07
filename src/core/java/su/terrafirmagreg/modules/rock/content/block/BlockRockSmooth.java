package su.terrafirmagreg.modules.rock.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Properties.BoolProp.CAN_FALL;


@SuppressWarnings("deprecation")
@Getter
public class BlockRockSmooth extends BaseBlock implements IRockEntry {
  
  protected final RockType type;

  public BlockRockSmooth(RockType type) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .ignoresProperties(CAN_FALL)
      .addOreDict("stoneSmooth")
      .addOreDict("stonePolished")
      .addOreDict("smooth")
      .addOreDict("smooth", type)
    );

    this.type = type;

    setDefaultState(getBlockState().getBaseState()
      .withProperty(CAN_FALL, false));

    FallingBlockManager.registerFallable(this.getDefaultState().withProperty(CAN_FALL, true),
      Specification.COLLAPSABLE_ROCK, BlocksRock.COBBLE.get(type).getDefaultState());
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(CAN_FALL, meta == 1);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer.Builder(this)
      .add(CAN_FALL)
      .build();
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(CAN_FALL) ? 1 : 0;
  }


}
