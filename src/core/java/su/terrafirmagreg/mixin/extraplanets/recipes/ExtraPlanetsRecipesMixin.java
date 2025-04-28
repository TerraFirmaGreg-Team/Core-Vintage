package su.terrafirmagreg.mixin.extraplanets.recipes;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.recipes.ExtraPlanets_Recipes;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(value = ExtraPlanets_Recipes.class, remap = false)
public class ExtraPlanetsRecipesMixin {

  @Inject(method = "registerSatellitesRecipes", at = @At(value = "HEAD"), cancellable = true)
  private static void onRegisterSatellitesRecipes(CallbackInfo ci) {
    HashMap<Object, Integer> inputMap;

    // Mercury
    inputMap = new HashMap<>();
    inputMap.put("circuitHv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.MERCURY_SPACE_STATION_ID, Config.MERCURY_ID, new SpaceStationRecipe(inputMap)));

    // Venus
    inputMap = new HashMap<>();
    inputMap.put("circuitHv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.VENUS_SPACE_STATION_ID, VenusModule.planetVenus.getDimensionID(), new SpaceStationRecipe(inputMap)));

    // Earth
    inputMap = new HashMap<>();
    inputMap.put("circuitHv", 16);
    inputMap.put("plateDoubleAluminium", 32);
    inputMap.put("plateDoubleTitanium", 32);
    inputMap.put("wireGtSingleUraniumTriplatinum", 32);
    GalacticraftRegistry.replaceSpaceStationRecipe(-27, inputMap);

    // Mars
    inputMap = new HashMap<>();
    inputMap.put("circuitHv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.MARS_SPACE_STATION_ID, MarsModule.planetMars.getDimensionID(), new SpaceStationRecipe(inputMap)));

    // Ceres
    inputMap = new HashMap<>();
    inputMap.put("circuitHv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.CERES_SPACE_STATION_ID, Config.CERES_ID, new SpaceStationRecipe(inputMap)));

    // Jupiter
    inputMap = new HashMap<>();
    inputMap.put("circuitEv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.JUPITER_SPACE_STATION_ID, Config.JUPITER_ID, new SpaceStationRecipe(inputMap)));

    // Saturn
    inputMap = new HashMap<>();
    inputMap.put("circuitEv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.SATURN_SPACE_STATION_ID, Config.SATURN_ID, new SpaceStationRecipe(inputMap)));

    // Uranus
    inputMap = new HashMap<>();
    inputMap.put("circuitEv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.URANUS_SPACE_STATION_ID, Config.URANUS_ID, new SpaceStationRecipe(inputMap)));

    // Neptune
    inputMap = new HashMap<>();
    inputMap.put("circuitIv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.NEPTUNE_SPACE_STATION_ID, Config.NEPTUNE_ID, new SpaceStationRecipe(inputMap)));

    // Pluto
    inputMap = new HashMap<>();
    inputMap.put("circuitIv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.PLUTO_SPACE_STATION_ID, Config.PLUTO_ID, new SpaceStationRecipe(inputMap)));

    // Eris
    inputMap = new HashMap<>();
    inputMap.put("circuitIv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.ERIS_SPACE_STATION_ID, Config.ERIS_ID, new SpaceStationRecipe(inputMap)));

    // Kepler22B
    inputMap = new HashMap<>();
    inputMap.put("circuitUhv", 32);
    inputMap.put("plateDoubleAluminium", 64);
    inputMap.put("plateDoubleTitanium", 64);
    inputMap.put("wireGtSingleUraniumTriplatinum", 64);
    GalacticraftRegistry.registerSpaceStation(new SpaceStationType(Config.KEPLER22B_SPACE_STATION_ID, Config.KEPLER22B_ID, new SpaceStationRecipe(inputMap)));

    ci.cancel();
  }
}
