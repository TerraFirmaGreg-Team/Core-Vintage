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

    public static void onRegister(RegistryManager registry) {

        OCEAN = registry.biome(new BiomeOcean());
        RIVER = registry.biome(new BiomeRiver());
        BEACH = registry.biome(new BiomeBeach());
        GRAVEL_BEACH = registry.biome(new BiomeGravelBeach());
        HIGH_HILLS = registry.biome(new BiomeHighHills());
        PLAINS = registry.biome(new BiomePlains());
        SWAMPLAND = registry.biome(new BiomeSwampland());
        HIGH_HILLS_EDGE = registry.biome(new BiomeHighHillsEdge());
        ROLLING_HILLS = registry.biome(new BiomeRollingHills());
        MOUNTAINS = registry.biome(new BiomeMountains());
        MOUNTAINS_EDGE = registry.biome(new BiomeMountainsEdge());
        HIGH_PLAINS = registry.biome(new BiomeHighPlains());
        DEEP_OCEAN = registry.biome(new BiomeDeepOcean());
        LAKE = registry.biome(new BiomeLake());
        FLATLANDS = registry.biome(new BiomeFlatlands());
        FIELDS = registry.biome(new BiomeFields());
        MEADOWS = registry.biome(new BiomeMeadows());
        BAYOU = registry.biome(new BiomeBayou());
        MANGROVE = registry.biome(new BiomeMangrove());
        MARSH = registry.biome(new BiomeMarsh());
        CRAG = registry.biome(new BiomeCrag());
        MESA = registry.biome(new BiomeMesa());
        MESA_PLATEAU = registry.biome(new BiomeMesaPlateau());
        MESA_PLATEAU_M = registry.biome(new BiomeMesaPlateauM());
        MESA_BRYCE = registry.biome(new BiomeMesaBryce());

    }
}
