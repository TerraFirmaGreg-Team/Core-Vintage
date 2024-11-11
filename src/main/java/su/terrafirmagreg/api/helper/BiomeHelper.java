package su.terrafirmagreg.api.helper;

import net.minecraft.world.biome.Biome;

import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;

public class BiomeHelper {

  public static boolean isMesaBiome(Biome biome) {
    return BiomesWorld.MESA == biome || BiomesWorld.MESA_PLATEAU == biome || BiomesWorld.MESA_BRYCE == biome || BiomesWorld.MESA_PLATEAU_M == biome;

  }

  public static boolean isOceanicBiome(Biome biome) {
    return BiomesWorld.OCEAN == biome || BiomesWorld.DEEP_OCEAN == biome || BiomesWorld.MANGROVE == biome;

  }

  public static boolean isRiverBiome(Biome biome) {
    return BiomesWorld.RIVER == biome;

  }

  public static boolean isLakeBiome(Biome biome) {
    return BiomesWorld.LAKE == biome;

  }

  public static boolean isMountainBiome(Biome biome) {
    return BiomesWorld.MOUNTAINS == biome || BiomesWorld.MOUNTAINS_EDGE == biome || BiomesWorld.CRAG == biome;

  }

  public static boolean isBeachBiome(Biome biome) {
    return BiomesWorld.BEACH == biome || BiomesWorld.GRAVEL_BEACH == biome;

  }
}
