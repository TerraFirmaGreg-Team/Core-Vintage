package su.terrafirmagreg.modules.flora.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.flora.api.types.variant.block.FloraBlockVariant;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlocksFlora {

  public static FloraBlockVariant PLANT;
  public static FloraBlockVariant FLOWER_POT;

  public static void onRegister(RegistryManager registryManager) {

    PLANT = FloraBlockVariant
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
