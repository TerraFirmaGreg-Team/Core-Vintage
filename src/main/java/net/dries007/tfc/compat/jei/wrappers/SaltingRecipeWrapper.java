package net.dries007.tfc.compat.jei.wrappers;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.objects.recipes.SaltingRecipe;

import java.util.ArrayList;
import java.util.List;

public class SaltingRecipeWrapper implements IRecipeWrapper {

  private final List<List<ItemStack>> input;
  private final List<List<ItemStack>> output;

  public SaltingRecipeWrapper(SaltingRecipe recipe) {
    input = new ArrayList<>();
    output = new ArrayList<>();

    List<ItemStack> listAllOutput = new ArrayList<>();
    for (Ingredient ingredient : recipe.getIngredients()) {
      List<ItemStack> slot = new ArrayList<>();
      for (ItemStack matchingStack : ingredient.getMatchingStacks()) {
        ItemStack stack = matchingStack.copy(); // Avoid changing recipe
        slot.add(stack);
        ICapabilityFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
        if (food != null) {
          // Clear food traits, for some reason, #getMatchingStacks grabs ingredients brined
          food.getTraits().clear();
          ItemStack outputStack = stack.copy();
          food = outputStack.getCapability(CapabilityFood.CAPABILITY, null);
          if (food != null) {
            food.getTraits().add(FoodTrait.SALTED);
          }
          listAllOutput.add(outputStack);
        }
      }
      input.add(slot);
    }
    this.output.add(listAllOutput);
  }


  @Override
  public void getIngredients(IIngredients ingredients) {
    ingredients.setInputLists(VanillaTypes.ITEM, input);
    ingredients.setOutputLists(VanillaTypes.ITEM, output);
  }
}
