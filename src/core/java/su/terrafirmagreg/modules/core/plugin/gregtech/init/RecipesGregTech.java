package su.terrafirmagreg.modules.core.plugin.gregtech.init;

import su.terrafirmagreg.modules.core.plugin.gregtech.recipes.OreRecipeCoreHandler;
import su.terrafirmagreg.modules.core.plugin.gregtech.recipes.ToolRecipeHandler;

public class RecipesGregTech {

  public static void postInit() {

    ToolRecipeHandler.register();
    OreRecipeCoreHandler.register();
  }
}
