package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

public class BlockWoodFenceGate extends BlockWoodFenceGateLog implements IWoodEntry {


  public BlockWoodFenceGate(WoodType type) {
    super(type);

    getSettings()
      .customResource(type.getResource("fence_gate"));
  }

}
