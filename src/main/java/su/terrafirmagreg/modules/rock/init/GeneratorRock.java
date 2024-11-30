package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.rock.object.generator.GeneratorRockLarge;
import su.terrafirmagreg.modules.rock.object.generator.GeneratorRockMossyRaw;
import su.terrafirmagreg.modules.rock.object.generator.GeneratorRockSpeleothem;
import su.terrafirmagreg.modules.rock.object.generator.GeneratorRockSurface;

import net.minecraftforge.fml.common.IWorldGenerator;

public final class GeneratorRock {

  public static IWorldGenerator MOSSY_RAW;
  public static IWorldGenerator LARGE_ROCK;
  public static IWorldGenerator SPELEOTHEM;
  public static IWorldGenerator SURFACE;

  public static void onRegister(RegistryManager registry) {

    MOSSY_RAW = registry.worldGenerator(new GeneratorRockMossyRaw());
    LARGE_ROCK = registry.worldGenerator(new GeneratorRockLarge());
    SPELEOTHEM = registry.worldGenerator(new GeneratorRockSpeleothem());
    SURFACE = registry.worldGenerator(new GeneratorRockSurface());
  }
}
