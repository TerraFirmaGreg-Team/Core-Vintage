package su.terrafirmagreg.modules.rock.plugin.gregtech.recipes;

import su.terrafirmagreg.modules.core.plugin.gregtech.unification.ore.oreprefix.OrePrefixCore;
import su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix.OrePrefixRock;

import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;

public class OreRecipeGregTech {

  public static void register() {
    OrePrefixCore.oreChunk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

    OrePrefixRock.oreRockSalt.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreBreccia.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreChalk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreChert.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreClaystone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreConglomerate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreDacite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreDolomite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreKomatiite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreMudstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreNovaculite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.orePeridotite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.orePorphyry.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreRhyolite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreSandstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreSchist.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreSiltstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixRock.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
  }
}
