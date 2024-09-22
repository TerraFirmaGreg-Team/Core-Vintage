package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;


public class BlockWoodFence extends BlockWoodFenceLog {


  public BlockWoodFence(WoodBlockVariant variant, WoodType type) {
    super(variant, type);

    getSettings()
            .customResource(variant.getCustomResource());

  }
}
