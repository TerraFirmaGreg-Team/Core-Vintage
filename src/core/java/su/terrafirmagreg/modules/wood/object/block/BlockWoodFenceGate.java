package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

public class BlockWoodFenceGate extends BlockWoodFenceGateLog implements IWoodEntry {


  public BlockWoodFenceGate(WoodType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("fence_gate"))
      .customResource(type.getResource("fence_gate"));
  }

}
