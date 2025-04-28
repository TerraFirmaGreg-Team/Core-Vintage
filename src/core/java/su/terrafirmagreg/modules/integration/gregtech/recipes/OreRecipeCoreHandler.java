package su.terrafirmagreg.modules.integration.gregtech.recipes;

import su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore;

import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;

public class OreRecipeCoreHandler {

  public static void register() {
    OrePrefixCore.oreChunk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

    OrePrefixCore.oreRockSalt.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreBreccia.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreChalk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreChert.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreClaystone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreConglomerate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreDacite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreDolomite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreKomatiite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreMudstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreNovaculite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.orePeridotite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.orePorphyry.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreRhyolite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreSandstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreSchist.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreSiltstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCore.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
  }
}
