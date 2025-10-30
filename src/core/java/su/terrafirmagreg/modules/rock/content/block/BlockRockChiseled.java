package su.terrafirmagreg.modules.rock.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockRockChiseled extends BaseBlock implements IRockEntry {

  protected final RockType type;

  public BlockRockChiseled(RockType type) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .addOreDict("stoneBrick")
      .addOreDict("brickStone")
      .addOreDict("chiseled")
      .addOreDict("chiseled", type)
    );

    this.type = type;

    FallingBlockManager.registerFallable(this, Specification.COLLAPSABLE_ROCK);
  }
}
