package su.terrafirmagreg.modules.rock.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockFalling;
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
public class BlockRockCobble extends BaseBlockFalling implements IRockEntry {

  protected final RockType type;

  public BlockRockCobble(RockType type) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .addOreDict("cobblestone")
      .addOreDict("cobble")
      .addOreDict("cobble", type)

    );

    this.type = type;
    setDefaultState(getBlockState().getBaseState()
      .withProperty(MOSSY, false));

    FallingBlockManager.registerFallable(this, Specification.VERTICAL_AND_HORIZONTAL_ROCK);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer.Builder(this)
      .add(MOSSY)
      .build();
  }
}
