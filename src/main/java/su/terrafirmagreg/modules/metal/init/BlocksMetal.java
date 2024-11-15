package su.terrafirmagreg.modules.metal.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariant;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalAnvil;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalCladding;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalLamp;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalPigvil;

public final class BlocksMetal {

  public static MetalBlockVariant ANVIL;
  public static MetalBlockVariant PIGVIL;
  public static MetalBlockVariant CLADDING;
  public static MetalBlockVariant LAMP;
  public static MetalBlockVariant TRAPDOOR;

  public static void onRegister(RegistryManager registryManager) {

    ANVIL = MetalBlockVariant
      .builder("anvil")
      .factory(BlockMetalAnvil::new)
      .build();

    PIGVIL = MetalBlockVariant
      .builder("pigvil")
      .factory(BlockMetalPigvil::new)
      .build();

    CLADDING = MetalBlockVariant
      .builder("cladding")
      .factory(BlockMetalCladding::new)
      .build();

    LAMP = MetalBlockVariant
      .builder("lamp")
      .factory(BlockMetalLamp::new)
      .build();

  }

}
