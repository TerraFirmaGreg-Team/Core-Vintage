package su.terrafirmagreg.api.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@UtilityClass
@SuppressWarnings("unused")
public final class BiomeUtils {

  /**
   * A cache of type names to their value. This is implemented to make type lookup much faster as forge does not offer this for some reason. This is populated
   * by the first call to {@link #getType(String)}
   */
  private static final Map<String, BiomeDictionary.Type> typeMap = new Object2ObjectOpenHashMap<>();

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

    final Set<Biome> biomes = new ObjectOpenHashSet<>();

    for (final BiomeDictionary.Type type : types) {

      if (type != null) {

        biomes.addAll(BiomeDictionary.getBiomes(type));
      }
    }

    return biomes;
  }

  public static boolean isBiomeDesired(Biome biome, Biome... biomes) {
    for (Biome b : biomes) {
      return b == biome;
    }
    return false;
  }

  public static Biome getBiomeForLoc(ResourceLocation location) {
    return Biome.REGISTRY.getObject(location);
  }

  public static List<Biome> getRegisteredBiomes() {
    return Lists.newArrayList(Biome.REGISTRY.iterator());
  }


  /**
   * Return the first valid biome type in this region, if any Almost certainly will return a value, but be sure to check for null
   *
   * @param temperature the average temperature
   * @param rainfall    the average rainfall
   * @param density     the average flora density
   * @return a BiomeType, if found any
   */
  @Nullable
  public static BiomeType getBiomeType(float temperature, float rainfall, float density) {
    for (BiomeType biomeType : BiomeType.values()) {
      if (biomeType.isValid(temperature, rainfall, density)) {
        // Return the first valid, should make forests have the highest priority and deserts the lowest
        return biomeType;
      }
    }
    return null;
  }

  /**
   * Get a list of valid biome types in this region. (probably useless, but w/e)
   *
   * @param temperature the average temperature
   * @param rainfall    the average rainfall
   * @param density     the average flora density
   * @return a list of BiomeType
   */
  @NotNull
  public static List<BiomeType> getValidBiomeTypes(float temperature, float rainfall, float density) {
    List<BiomeType> biomeTypes = new ArrayList<>();
    for (BiomeType biomeType : BiomeType.values()) {
      if (biomeType.isValid(temperature, rainfall, density)) {
        biomeTypes.add(biomeType);
      }
    }
    return biomeTypes;
  }

  public enum BiomeType {
    // Ordered by priority.
    TROPICAL_FOREST(19, 100, 60, 500, 0.25f, 1), // Forests in a hot region
    TEMPERATE_FOREST(-2, 22, 60, 500, 0.25f, 1), // Forests in a mild temperature region
    TAIGA(-15, 6, 60, 500, 0.25f, 1), // Forests in a cold region

    // Regions where you won't find much trees
    PLAINS(0, 22, 60, 500, 0, 0.25f), // Low number of trees and mild temperatures
    SAVANNA(19, 100, 60, 500, 0, 0.3f), // Low number of trees and high temperatures

    TUNDRA(-100, 0, 0, 500, 0, 1), // Cold deserts
    DESERT(0, 100, 0, 60, 0, 1); // No trees and mild-high temperature

    private final float temperatureMin;
    private final float temperatureMax;
    private final float rainfallMin;
    private final float rainfallMax;
    private final float densityMin;
    private final float densityMax;

    BiomeType(float temperatureMin, float temperatureMax, float rainfallMin, float rainfallMax, float densityMin, float densityMax) {
      this.temperatureMin = temperatureMin;
      this.temperatureMax = temperatureMax;

      this.rainfallMin = rainfallMin;
      this.rainfallMax = rainfallMax;

      this.densityMin = densityMin;
      this.densityMax = densityMax;
    }

    public boolean isValid(float temperature, float rainfall, float density) {
      return rainfall >= rainfallMin && rainfall <= rainfallMax &&
             temperature >= temperatureMin && temperature <= temperatureMax &&
             density >= densityMin && density <= densityMax;
    }
  }
}
