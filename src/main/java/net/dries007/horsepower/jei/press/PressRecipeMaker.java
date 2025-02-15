package net.dries007.horsepower.jei.press;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.dries007.horsepower.recipes.HPRecipes;
import net.dries007.horsepower.recipes.PressRecipe;

public class PressRecipeMaker {

  public static List<PressRecipeWrapper> getPressItemRecipes(IJeiHelpers helpers) {
    IStackHelper stackHelper = helpers.getStackHelper();
    Collection<PressRecipe> pressRecipes = HPRecipes.instance().getPressRecipes();

    List<PressRecipeWrapper> recipes = new ArrayList<>();

    for (PressRecipe recipe : pressRecipes) {
      if (recipe.isLiquidRecipe()) {continue;}
      ItemStack input = recipe.getInput();
      ItemStack output = recipe.getOutput();

      List<ItemStack> inputs = stackHelper.getSubtypes(input);
      PressRecipeWrapper pressRecipeWrapper = new PressRecipeWrapper(inputs, output, null);
      recipes.add(pressRecipeWrapper);
    }

    return recipes;
  }

  public static List<PressRecipeWrapper> getPressFluidRecipes(IJeiHelpers helpers) {
    IStackHelper stackHelper = helpers.getStackHelper();
    Collection<PressRecipe> pressRecipes = HPRecipes.instance().getPressRecipes();

    List<PressRecipeWrapper> recipes = new ArrayList<>();

    for (PressRecipe recipe : pressRecipes) {
      if (!recipe.isLiquidRecipe()) {continue;}
      ItemStack input = recipe.getInput();
      FluidStack fluidOutput = recipe.getOutputFluid();

      List<ItemStack> inputs = stackHelper.getSubtypes(input);
      PressRecipeWrapper pressRecipeWrapper = new PressRecipeWrapper(inputs, null, fluidOutput);
      recipes.add(pressRecipeWrapper);
    }

    return recipes;
  }
}
