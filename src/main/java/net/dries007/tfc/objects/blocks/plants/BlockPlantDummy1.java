package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

import java.util.HashMap;
import java.util.Map;

public class BlockPlantDummy1 extends BlockPlantTFCF {

  private static final Map<PlantType, BlockPlantDummy1> MAP = new HashMap<>();

  public BlockPlantDummy1(PlantType plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }

    plant.getOreDictName().ifPresent(name -> OreDictUtils.register(this, name));
  }

  public static BlockPlantDummy1 get(PlantType plant) {
    return MAP.get(plant);
  }
}
