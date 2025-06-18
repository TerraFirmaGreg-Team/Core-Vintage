package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.feature.woodtype.spi.IWoodBlock;

public class BlockWoodFenceGate extends BlockWoodFenceGateLog implements IWoodBlock {


  public BlockWoodFenceGate(WoodType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("fence_gate"))
      .customResource(type.getResource("fence_gate"));
  }

}
