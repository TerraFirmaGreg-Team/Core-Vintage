package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodPlanks extends BlockWood {

  public BlockWoodPlanks(WoodBlockVariant variant, WoodType type) {
    super(variant, type);

    getSettings()
            .hardness(2.0F)
            .resistance(5.0F);
  }
}
