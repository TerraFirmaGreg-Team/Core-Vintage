package su.terrafirmagreg.modules.world.classic.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorLargeRocks;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorMossyRaw;

import net.minecraftforge.fml.common.IWorldGenerator;

public final class GeneratorWorld {

  public static IWorldGenerator MOSSY_RAW;
  public static IWorldGenerator LARGE_ROCKS;

  public static void onRegister(RegistryManager registryManager) {

    MOSSY_RAW = registryManager.worldGenerator(new GeneratorMossyRaw());
    LARGE_ROCKS = registryManager.worldGenerator(new GeneratorLargeRocks());
  }
}
