package net.dries007.eerussianguy.firmalife.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.compat.jei.IJEISimpleRecipe;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DryingRecipe extends IForgeRegistryEntry.Impl<DryingRecipe> implements IJEISimpleRecipe {

  private final int duration;
  protected IIngredient<ItemStack> inputItem;
  protected ItemStack outputItem;

  public DryingRecipe(IIngredient<ItemStack> input, ItemStack output, int duration) {
    this.inputItem = input;
    this.outputItem = output;
    this.duration = duration;

    if (inputItem == null || outputItem == null) {
      throw new IllegalArgumentException("Sorry, but you can't have drying recipes that don't have an input and output.");
    }
    if (duration < 1) {
      throw new IllegalArgumentException("Sorry, but drying recipes have to have a duration.");
    }
  }

  @Nullable
  public static DryingRecipe get(ItemStack item) {
    return TFCRegistries.DRYING.getValuesCollection()
                               .stream()
                               .filter(x -> x.isValidInput(item))
                               .findFirst()
                               .orElse(null);
  }

  public static int getDuration(DryingRecipe recipe) {
    return recipe.duration;
  }

  private boolean isValidInput(ItemStack inputItem) {
    return this.inputItem.test(inputItem);
  }

  @NotNull
  public ItemStack getOutputItem(ItemStack stack) {
    return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());
  }

  // for JEI
  @Override
  public NonNullList<IIngredient<ItemStack>> getIngredients() {
    return NonNullList.withSize(1, inputItem);
  }

  @Override
  public NonNullList<ItemStack> getOutputs() {
    return NonNullList.withSize(1, outputItem);
  }
}
