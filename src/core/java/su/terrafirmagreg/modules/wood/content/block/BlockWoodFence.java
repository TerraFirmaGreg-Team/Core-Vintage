package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;


public class BlockWoodFence extends BlockWoodFenceLog {


  public BlockWoodFence(WoodType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("fence"))
      .customResource(type.getResource("fence"));

  }
}
