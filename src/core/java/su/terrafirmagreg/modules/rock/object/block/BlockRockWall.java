package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockWall;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockRockWall extends BaseBlockWall implements IRockEntry {

  private final RockType type;

  public BlockRockWall(Block model, RockType type) {
    super(model);

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("variant"))
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .addOreDict("wall")
      .addOreDict("wall", "stone");
  }
}
