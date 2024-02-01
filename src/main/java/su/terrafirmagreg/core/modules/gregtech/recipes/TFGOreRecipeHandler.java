package su.terrafirmagreg.core.modules.gregtech.recipes;

import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix;

public class TFGOreRecipeHandler {

    public static void register() {
        TFGOrePrefix.oreChunk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

        TFGOrePrefix.oreRockSalt.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreBreccia.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreChalk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreChert.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreClaystone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreConglomerate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreDacite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreDolomite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreKomatiite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreMudstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreNovaculite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.orePeridotite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.orePorphyry.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreRhyolite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSandstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSchist.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSiltstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    }
}
