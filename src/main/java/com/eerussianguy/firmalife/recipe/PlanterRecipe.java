package com.eerussianguy.firmalife.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;


import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlanterRecipe extends IForgeRegistryEntry.Impl<PlanterRecipe> {

  private final int stages;
  private final boolean large;
  private final int tier;
  protected IIngredient<ItemStack> inputItem;
  protected ItemStack outputItem;

  public PlanterRecipe(IIngredient<ItemStack> input, ItemStack output, int stages, boolean large) {
    this(input, output, stages, large, 0);
  }

  public PlanterRecipe(IIngredient<ItemStack> input, ItemStack output, int stages, boolean large, int tier) {
    this.inputItem = input;
    this.outputItem = output;
    this.stages = stages;
    this.large = large;
    this.tier = tier;

    if (inputItem == null || outputItem == null) {
      throw new IllegalArgumentException("Sorry, but the planter needs inputs and outputs.");
    }
    if (stages < 1) {
      throw new IllegalArgumentException("Sorry, but crops need have to have stages.");
    }
  }

  @Nullable
  public static PlanterRecipe get(ItemStack item) {
    return TFCRegistries.PLANTER_QUAD.getValuesCollection()
            .stream()
            .filter(x -> x.isValidInput(item))
            .findFirst()
            .orElse(null);
  }

  private boolean isValidInput(ItemStack inputItem) {
    return this.inputItem.test(inputItem);
  }

  public static int getMaxStage(PlanterRecipe recipe) {
    return recipe.stages;
  }

  public static int getTier(PlanterRecipe recipe) {
    return recipe.tier;
  }

  public boolean isLarge() {
    return large;
  }

  // Using this for HWYLA access
  @NotNull
  public ItemStack getOutputItem() {
    return outputItem;
  }

  @NotNull
  public ItemStack getOutputItem(ItemStack stack) {
    return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());
  }

  @Nullable
  public static class PlantInfo {

    private final PlanterRecipe recipe;
    private final int stage;

    public PlantInfo(PlanterRecipe recipe, int stage) {
      this.recipe = recipe;
      this.stage = stage;
    }

    public PlanterRecipe getRecipe() {
      return recipe;
    }

    public int getStage() {
      return stage;
    }
  }
}
