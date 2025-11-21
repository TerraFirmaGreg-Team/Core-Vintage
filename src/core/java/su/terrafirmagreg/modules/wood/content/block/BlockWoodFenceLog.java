package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockFence;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodFenceLog extends BaseBlockFence implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodFenceLog(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .sound(SoundType.WOOD)
      .hardness(2.0F)
      .resistance(15.0F)
      .harvestLevel(ToolClasses.AXE, 0)
      .fireInfo(5, 20)
      .addOreDict("fence", "log")
      .addOreDict("fence", "log", type)
    );

    this.type = type;

  }
}
