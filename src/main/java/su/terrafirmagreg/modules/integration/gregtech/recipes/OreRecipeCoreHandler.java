package su.terrafirmagreg.modules.integration.gregtech.recipes;

import su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler;

import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;

public class OreRecipeCoreHandler {

  public static void register() {
    OrePrefixHandler.oreChunk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

    OrePrefixHandler.oreRockSalt.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreBreccia.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreChalk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreChert.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreClaystone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreConglomerate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreDacite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreDolomite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreKomatiite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreMudstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreNovaculite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.orePeridotite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.orePorphyry.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreRhyolite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreSandstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreSchist.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreSiltstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixHandler.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
  }
}
