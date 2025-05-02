package su.terrafirmagreg.modules.integration.gregtech.init;

import su.terrafirmagreg.modules.integration.gregtech.recipes.OreRecipeCoreHandler;
import su.terrafirmagreg.modules.integration.gregtech.recipes.ToolRecipeHandler;

public class RecipesGregTech {

  public static void postInit() {

    ToolRecipeHandler.register();
    OreRecipeCoreHandler.register();
  }
}
