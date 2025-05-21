package su.terrafirmagreg.api.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
@SuppressWarnings("unused")
public final class BiomeUtils {

  /**
   * A cache of type names to their value. This is implemented to make type lookup much faster as forge does not offer this for some reason. This is populated by the first call to {@link #getType(String)}
   */
  private static final Map<String, BiomeDictionary.Type> TYPE_MAP = new Object2ObjectOpenHashMap<>();
  public static final List<Biome> SPAWN_BIOMES = new ObjectArrayList<>();
  public static final List<Biome> WORLD_GEN_BIOMES = new ObjectArrayList<>();

  // Distinct colors for debug map gen
  private static final Color[] COLORS = new Color[]{
    new Color(0xFFB300),    // Vivid Yellow
    new Color(0x803E75),    // Strong Purple
    new Color(0xFF6800),    // Vivid Orange
    new Color(0xA6BDD7),    // Very Light Blue
    new Color(0xC10020),    // Vivid Red
    new Color(0xCEA262),    // Grayish Yellow
    new Color(0x817066),    // Medium Gray
    new Color(0x007D34),    // Vivid Green
    new Color(0xF6768E),    // Strong Purplish Pink
    new Color(0x00538A),    // Strong Blue
    new Color(0xFF7A5C),    // Strong Yellowish Pink
    new Color(0x53377A),    // Strong Violet
    new Color(0xFF8E00),    // Vivid Orange Yellow
    new Color(0xB32851),    // Strong Purplish Red
    new Color(0xF4C800),    // Vivid Greenish Yellow
    new Color(0x7F180D),    // Strong Reddish Brown
    new Color(0x93AA00),    // Vivid Yellowish Green
    new Color(0x593315),    // Deep Yellowish Brown
    new Color(0xF13A13),    // Vivid Reddish Orange
    new Color(0x232C16),    // Dark Olive Green
  };


  public static void addTypes(Biome biome, List<Type> types) {
    if (!types.isEmpty()) {
      BiomeDictionary.addTypes(biome, types.toArray(new Type[0]));
    }
  }

  public static void addSpawn(Biome biome, boolean isSpawn) {
    if (isSpawn) {
      BiomeManager.addSpawnBiome(biome);
      SPAWN_BIOMES.add(biome);
    }
  }

  public static void addWorldGen(Biome biome, boolean isWorldGen) {
    if (isWorldGen) {
      WORLD_GEN_BIOMES.add(biome);
    }
  }

  public static List<Biome> getBiomesForModID(String modId) {
    return ForgeRegistries.BIOMES.getValuesCollection().stream()
      .filter(biome -> biome.getRegistryName() != null)
      .filter(biome -> biome.getRegistryName().getNamespace().equals(modId))
      .collect(Collectors.toList());
  }


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

    if (TYPE_MAP.isEmpty()) {
      for (final BiomeDictionary.Type type : BiomeDictionary.Type.getAll()) {
        TYPE_MAP.put(type.getName(), type);
      }
    }

    return TYPE_MAP.get(name.toUpperCase());
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
