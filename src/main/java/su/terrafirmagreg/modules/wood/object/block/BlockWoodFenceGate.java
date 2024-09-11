package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceGate extends BlockWoodFenceGateLog implements IWoodBlock {


  public BlockWoodFenceGate(WoodBlockVariant variant, WoodType type) {
    super(variant, type);

    getSettings()
            .customResource(String.format("wood/%s", variant));
  }

}
