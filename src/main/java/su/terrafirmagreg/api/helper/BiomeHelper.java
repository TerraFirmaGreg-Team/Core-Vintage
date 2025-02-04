package su.terrafirmagreg.api.helper;

import net.minecraft.world.biome.Biome;

import net.dries007.tfc.world.classic.biomes.BiomesTFC;

public class BiomeHelper {

  public static boolean isMesa(Biome biome) {
    return BiomesTFC.MESA == biome || BiomesTFC.MESA_PLATEAU == biome || BiomesTFC.MESA_BRYCE == biome || BiomesTFC.MESA_PLATEAU_M == biome;
  }

  public static boolean isOceanic(Biome biome) {
    return BiomesTFC.OCEAN == biome || BiomesTFC.DEEP_OCEAN == biome || BiomesTFC.MANGROVE == biome;
  }

  public static boolean isRiver(Biome biome) {
    return BiomesTFC.RIVER == biome;
  }

  public static boolean isLake(Biome biome) {
    return BiomesTFC.LAKE == biome;
  }

  public static boolean isMountain(Biome biome) {
    return BiomesTFC.MOUNTAINS == biome || BiomesTFC.MOUNTAINS_EDGE == biome || BiomesTFC.CRAG == biome;
  }

  public static boolean isBeach(Biome biome) {
    return BiomesTFC.BEACH == biome || BiomesTFC.GRAVEL_BEACH == biome;
  }
}
