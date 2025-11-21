package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockFenceGate;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodFenceGate extends BaseBlockFenceGate implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodFenceGate(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .ignoresProperties(IN_WALL, POWERED)
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .hardness(2.0F)
      .resistance(15.0F)
      .fireInfo(5, 20)
      .customResource(type.getResource("fence_gate"))
      .addOreDict("fence", "gate", "wood")
      .addOreDict("fence", "gate", "wood", type));

    this.type = type;

  }

}
