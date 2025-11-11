package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

public class BlockWoodFenceGate extends BlockWoodFenceGateLog implements IWoodEntry {


  public BlockWoodFenceGate(WoodType type) {
    super(type);

    getSettings()
      .customResource(type.getResource("fence_gate"));
  }

}
