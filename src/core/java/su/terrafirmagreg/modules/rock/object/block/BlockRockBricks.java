package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Properties.BoolProp.MOSSY;

@Getter
public class BlockRockBricks extends BaseBlock implements IRockEntry {

  public static final String NAME = "bricks";
  protected final RockType type;

  public BlockRockBricks(RockType type) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .registryKey(type.getRegistryKey(NAME))
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .addOreDict("stoneBrick")
      .addOreDict("brickStone")
      .addOreDict(NAME)
      .addOreDict(NAME, type)
    );

    this.type = type;

    setDefaultState(getBlockState().getBaseState()
      .withProperty(MOSSY, false));

    FallingBlockManager.registerFallable(this, Specification.COLLAPSABLE_ROCK);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer.Builder(this)
      .add(MOSSY)
      .build();
  }
}
