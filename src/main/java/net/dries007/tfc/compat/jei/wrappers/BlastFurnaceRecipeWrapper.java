package net.dries007.tfc.compat.jei.wrappers;

import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.recipes.BlastFurnaceRecipe;
import net.dries007.tfc.compat.jei.TFCJEIPlugin;

import java.util.ArrayList;
import java.util.List;

public class BlastFurnaceRecipeWrapper implements IRecipeWrapper {

  private final List<ItemStack> ingredients;
  private final List<ItemStack> additives;
  private final ItemStack output;

  public BlastFurnaceRecipeWrapper(BlastFurnaceRecipe recipe) {
    ingredients = new ArrayList<>();
    additives = new ArrayList<>();
    // Although this looks resource-intensive, it's done one time only
    TFCJEIPlugin.getAllIngredients().forEach(stack -> {
      if (recipe.isValidInput(stack)) {
        ingredients.add(stack);
      } else if (recipe.isValidAdditive(stack)) {
        additives.add(stack);
      }
    });
    output = recipe.getOutput();
  }

  @Override
  public void getIngredients(IIngredients recipeIngredients) {
    List<List<ItemStack>> allInputs = new ArrayList<>();
    allInputs.add(ingredients);
    allInputs.add(additives);
    recipeIngredients.setInputLists(VanillaTypes.ITEM, allInputs);

    List<List<ItemStack>> allOutputs = new ArrayList<>();
    allOutputs.add(Lists.newArrayList(output));
    recipeIngredients.setOutputLists(VanillaTypes.ITEM, allOutputs);
  }
}
