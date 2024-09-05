package su.terrafirmagreg.modules.device.plugin.jei.quern;

import su.terrafirmagreg.api.plugin.jei.wrapper.BaseRecipeWrapper;
import su.terrafirmagreg.modules.device.objects.recipes.quern.IQuernRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;


import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

import java.util.ArrayList;
import java.util.List;

public class QuernRecipeWrapper extends BaseRecipeWrapper<IQuernRecipe> {

  public QuernRecipeWrapper(IQuernRecipe recipe) {
    super(recipe);
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    IQuernRecipe recipe = getRecipe();

    List<List<ItemStack>> allInputs = new ArrayList<>();
    allInputs.add(recipe.getInputItem().getValidIngredients());

    ingredients.setInputLists(VanillaTypes.ITEM, allInputs);

    List<List<ItemStack>> allOutputs = new ArrayList<>();
    allOutputs.add(NonNullList.withSize(1, recipe.getOutputItem()));

    ingredients.setOutputLists(VanillaTypes.ITEM, allOutputs);

  }
}
