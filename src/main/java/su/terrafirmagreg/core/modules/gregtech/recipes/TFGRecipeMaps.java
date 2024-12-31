package su.terrafirmagreg.core.modules.gregtech.recipes;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;

public class TFGRecipeMaps {

  public static final RecipeMap<SimpleRecipeBuilder> GREENHOUSE_RECIPES = new RecipeMap<>("greenhouse", 4, 4, 1, 1, new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.COOLING);

}
