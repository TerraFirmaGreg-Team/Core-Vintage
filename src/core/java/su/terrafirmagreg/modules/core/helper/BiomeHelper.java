package su.terrafirmagreg.modules.core.helper;

import su.terrafirmagreg.modules.world.init.BiomesWorld;

import net.minecraft.world.biome.Biome;

public class BiomeHelper {

  public static boolean isMesa(Biome biome) {
    return BiomesWorld.MESA == biome || BiomesWorld.MESA_PLATEAU == biome || BiomesWorld.MESA_BRYCE == biome || BiomesWorld.MESA_PLATEAU_M == biome;
  }

  public static boolean isOceanic(Biome biome) {
    return BiomesWorld.OCEAN == biome || BiomesWorld.DEEP_OCEAN == biome || BiomesWorld.MANGROVE == biome;
  }

  public static boolean isRiver(Biome biome) {
    return BiomesWorld.RIVER == biome;
  }

  public static boolean isLake(Biome biome) {
    return BiomesWorld.LAKE == biome;
  }

  public static boolean isMountain(Biome biome) {
    return BiomesWorld.MOUNTAINS == biome || BiomesWorld.MOUNTAINS_EDGE == biome || BiomesWorld.CRAG == biome;
  }

  public static boolean isBeach(Biome biome) {
    return BiomesWorld.BEACH == biome || BiomesWorld.GRAVEL_BEACH == biome;
  }
}
