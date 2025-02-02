package net.dries007.tfcflorae.objects.blocks.plants.BlockPlant;

import net.dries007.tfc.api.types.Plant;
import net.dries007.tfcflorae.util.OreDictionaryHelper;

import java.util.HashMap;
import java.util.Map;

public class BlockPlantDummy2 extends BlockPlantTFCF {

  private static final Map<Plant, BlockPlantDummy2> MAP = new HashMap<>();

  public BlockPlantDummy2(Plant plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {throw new IllegalStateException("There can only be one.");}

    plant.getOreDictName().ifPresent(name -> OreDictionaryHelper.register(this, name));
  }

  public static BlockPlantDummy2 get(Plant plant) {
    return MAP.get(plant);
  }

}
