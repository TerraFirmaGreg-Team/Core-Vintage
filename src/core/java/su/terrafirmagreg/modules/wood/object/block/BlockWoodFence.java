package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;


public class BlockWoodFence extends BlockWoodFenceLog {


  public BlockWoodFence(WoodType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("fence"))
      .customResource(type.getResource("fence"));

  }
}
