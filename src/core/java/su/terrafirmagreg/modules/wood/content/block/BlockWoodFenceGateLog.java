package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockFenceGate;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodFenceGateLog extends BaseBlockFenceGate implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodFenceGateLog(WoodType type) {

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("fence_gate_log"))
      .ignoresProperties(IN_WALL, POWERED)
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .hardness(2.0F)
      .resistance(15.0F)
      .fireInfo(5, 20)
      .addOreDict("fence", "gate", "wood")
      .addOreDict("fence", "gate", "wood", type);

  }
}
