package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.block.spi.BlockWood;

public class BlockWoodPlanks extends BlockWood {

  public BlockWoodPlanks(WoodType type) {
    super(type, "planks");

    getSettings()
      .hardness(2.0F)
      .resistance(5.0F);
  }
}
