package net.dries007.tfc.objects.inventory.ingredient;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Accepts only valid, not rotten foods on recipes
 */
public class IngredientItemFood implements IIngredient<ItemStack> {

  private final IIngredient<ItemStack> innerIngredient;

  public IngredientItemFood(IIngredient<ItemStack> innerIngredient) {
    this.innerIngredient = innerIngredient;
  }

  @Override
  public NonNullList<ItemStack> getValidIngredients() {
    NonNullList<ItemStack> ingredients = innerIngredient.getValidIngredients();
    for (ItemStack stack : ingredients) {
      ICapabilityFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
      if (food != null) {
        food.setNonDecaying();
      }
    }
    return ingredients;
  }

  @Override
  public boolean test(ItemStack input) {
    return innerIngredient.test(input) && !isRotten(input);
  }

  @Override
  public boolean testIgnoreCount(ItemStack stack) {
    return innerIngredient.testIgnoreCount(stack) && !isRotten(stack);
  }

  @Override
  public ItemStack consume(ItemStack input) {
    return innerIngredient.consume(input);
  }

  @Override
  public int getAmount() {
    return innerIngredient.getAmount();
  }

  private boolean isRotten(ItemStack stack) {
    ICapabilityFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
    return cap != null && cap.isRotten();
  }
}
