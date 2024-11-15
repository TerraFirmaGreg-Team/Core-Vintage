package su.terrafirmagreg.modules.plant.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.plant.api.types.variant.block.PlantBlockVariant;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlocksPlant {

  public static PlantBlockVariant PLANT;
  public static PlantBlockVariant FLOWER_POT;

  public static void onRegister(RegistryManager registryManager) {

    PLANT = PlantBlockVariant
      .builder("plant")
      .factory((variant, type) -> type.getCategory().create(variant, type))
      .build();

//    FLOWER_POT = PlantBlockVariant
//      .builder("flower_pot")
//      .factory(BlockPlantFlowerPot::new)
//      .build();
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(RegistryManager registryManager) {

  }
}
