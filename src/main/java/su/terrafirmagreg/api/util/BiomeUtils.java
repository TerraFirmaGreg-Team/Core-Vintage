package su.terrafirmagreg.api.util;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;


import com.google.common.collect.Lists;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.BEACH;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.CRAG;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.DEEP_OCEAN;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.GRAVEL_BEACH;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.LAKE;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.MANGROVE;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.MESA;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.MESA_BRYCE;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.MESA_PLATEAU;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.MESA_PLATEAU_M;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.MOUNTAINS;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.MOUNTAINS_EDGE;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.OCEAN;
import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.RIVER;

@UtilityClass
@SuppressWarnings("unused")
public final class BiomeUtils {

  /**
   * A cache of type names to their value. This is implemented to make type lookup much faster as forge does not offer this for some reason. This is
   * populated by the first call to {@link #getType(String)}
   */
  private static final Map<String, BiomeDictionary.Type> typeMap = new HashMap<>();

  /**
   * Gets a list of biomes for a type string.
   *
   * @param typeName The name of the type. This should be upper case.
   * @return The biomes of that type.
   */
  public static Set<Biome> getBiomesForType(String typeName) {

    final BiomeDictionary.Type type = getType(typeName);
    return getBiomesForTypes(type);
  }

  /**
   * Gets a type by it's string name.
   *
   * @param name The name to look for. This should be upper case.
   * @return The biome type.
   */
  public static BiomeDictionary.Type getType(String name) {

    if (typeMap.isEmpty()) {

      for (final BiomeDictionary.Type type : BiomeDictionary.Type.getAll()) {

        typeMap.put(type.getName(), type);
      }
    }

    return typeMap.get(name.toUpperCase());
  }

  /**
   * Gets a set of biomes for multiple types.
   *
   * @param types The types to get for.
   * @return A set of the biomes.
   */
  public static Set<Biome> getBiomesForTypes(BiomeDictionary.Type... types) {

    final Set<Biome> biomes = new HashSet<>();

    for (final BiomeDictionary.Type type : types) {

      if (type != null) {

        biomes.addAll(BiomeDictionary.getBiomes(type));
      }
    }

    return biomes;
  }

  public static List<Biome> getRegisteredBiomes() {
    return Lists.newArrayList(Biome.REGISTRY.iterator());
  }

  public static boolean isMesaBiome(Biome biome) {
    return MESA == biome || MESA_PLATEAU == biome || MESA_BRYCE == biome || MESA_PLATEAU_M == biome;

  }

  public static boolean isOceanicBiome(Biome biome) {
    return OCEAN == biome || DEEP_OCEAN == biome || MANGROVE == biome;

  }

  public static boolean isRiverBiome(Biome biome) {
    return RIVER == biome;

  }

  public static boolean isLakeBiome(Biome biome) {
    return LAKE == biome;

  }

  public static boolean isMountainBiome(Biome biome) {
    return MOUNTAINS == biome || MOUNTAINS_EDGE == biome || CRAG == biome;

  }

  public static boolean isBeachBiome(Biome biome) {
    return BEACH == biome || GRAVEL_BEACH == biome;

  }
}
