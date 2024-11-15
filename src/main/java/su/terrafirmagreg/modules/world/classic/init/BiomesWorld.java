package su.terrafirmagreg.modules.world.classic.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeBayou;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeBeach;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeCrag;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeDeepOcean;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeFields;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeFlatlands;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeGravelBeach;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeHighHills;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeHighHillsEdge;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeHighPlains;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeLake;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMangrove;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMarsh;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMeadows;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMesa;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMesaBryce;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMesaPlateau;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMesaPlateauM;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMountains;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeMountainsEdge;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeOcean;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomePlains;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeRiver;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeRollingHills;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.BiomeSwampland;

import net.minecraft.world.biome.Biome;

public final class BiomesWorld {

  public static Biome OCEAN;
  public static Biome RIVER;
  public static Biome BEACH;
  public static Biome GRAVEL_BEACH;
  public static Biome HIGH_HILLS;
  public static Biome PLAINS;
  public static Biome SWAMPLAND;
  public static Biome HIGH_HILLS_EDGE;
  public static Biome ROLLING_HILLS;
  public static Biome MOUNTAINS;
  public static Biome MOUNTAINS_EDGE;
  public static Biome HIGH_PLAINS;
  public static Biome DEEP_OCEAN;
  public static Biome LAKE;
  public static Biome FLATLANDS;
  public static Biome FIELDS;
  public static Biome MEADOWS;
  public static Biome BAYOU;
  public static Biome MANGROVE;
  public static Biome MARSH;
  public static Biome CRAG;
  public static Biome MESA;
  public static Biome MESA_PLATEAU;
  public static Biome MESA_PLATEAU_M;
  public static Biome MESA_BRYCE;

  public static void onRegister(RegistryManager registryManager) {

    OCEAN = registryManager.biome(new BiomeOcean());
    RIVER = registryManager.biome(new BiomeRiver());
    BEACH = registryManager.biome(new BiomeBeach());
    GRAVEL_BEACH = registryManager.biome(new BiomeGravelBeach());
    HIGH_HILLS = registryManager.biome(new BiomeHighHills());
    PLAINS = registryManager.biome(new BiomePlains());
    SWAMPLAND = registryManager.biome(new BiomeSwampland());
    HIGH_HILLS_EDGE = registryManager.biome(new BiomeHighHillsEdge());
    ROLLING_HILLS = registryManager.biome(new BiomeRollingHills());
    MOUNTAINS = registryManager.biome(new BiomeMountains());
    MOUNTAINS_EDGE = registryManager.biome(new BiomeMountainsEdge());
    HIGH_PLAINS = registryManager.biome(new BiomeHighPlains());
    DEEP_OCEAN = registryManager.biome(new BiomeDeepOcean());
    LAKE = registryManager.biome(new BiomeLake());
    FLATLANDS = registryManager.biome(new BiomeFlatlands());
    FIELDS = registryManager.biome(new BiomeFields());
    MEADOWS = registryManager.biome(new BiomeMeadows());
    BAYOU = registryManager.biome(new BiomeBayou());
    MANGROVE = registryManager.biome(new BiomeMangrove());
    MARSH = registryManager.biome(new BiomeMarsh());
    CRAG = registryManager.biome(new BiomeCrag());
    MESA = registryManager.biome(new BiomeMesa());
    MESA_PLATEAU = registryManager.biome(new BiomeMesaPlateau());
    MESA_PLATEAU_M = registryManager.biome(new BiomeMesaPlateauM());
    MESA_BRYCE = registryManager.biome(new BiomeMesaBryce());

  }
}
