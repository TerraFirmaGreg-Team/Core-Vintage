package net.dries007.tfc.objects.blocks.plants.BlockPlant;

import net.dries007.tfc.api.types.Plant;
import tfcflorae.util.OreDictionaryHelper;

import java.util.HashMap;
import java.util.Map;

public class BlockPlantDummy1 extends BlockPlantTFCF {

  private static final Map<Plant, BlockPlantDummy1> MAP = new HashMap<>();

  public BlockPlantDummy1(Plant plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }

    plant.getOreDictName().ifPresent(name -> OreDictionaryHelper.register(this, name));
  }

  public static BlockPlantDummy1 get(Plant plant) {
    return MAP.get(plant);
  }
}
