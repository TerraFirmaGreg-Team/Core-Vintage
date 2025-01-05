package su.terrafirmagreg.modules.core.plugin.gregtech.recipes;

import su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler;

import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;

public class OreRecipeCoreHandler {

  public static void register() {
    OrePrefixCoreHandler.oreChunk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

    OrePrefixCoreHandler.oreRockSalt.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreBreccia.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreChalk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreChert.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreClaystone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreConglomerate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreDacite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreDolomite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreKomatiite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreMudstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreNovaculite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.orePeridotite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.orePorphyry.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreRhyolite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreSandstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreSchist.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreSiltstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    OrePrefixCoreHandler.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
  }
}
