package net.dries007.tfc.objects.inventory.ingredient;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class IngredientItemFoodTrait implements IIngredient<ItemStack> {

  private final IIngredient<ItemStack> innerIngredient;
  private final FoodTrait trait;

  public IngredientItemFoodTrait(IIngredient<ItemStack> innerIngredient, FoodTrait trait) {
    this.innerIngredient = innerIngredient;
    this.trait = trait;
  }

  @Override
  public NonNullList<ItemStack> getValidIngredients() {
    NonNullList<ItemStack> ingredients = innerIngredient.getValidIngredients();
    for (ItemStack stack : ingredients) {
      ICapabilityFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
      if (food != null) {
        CapabilityFood.applyTrait(food, trait);
      }
    }
    return ingredients;
  }

  @Override
  public boolean test(ItemStack input) {
    return innerIngredient.test(input) && hasTrait(input);
  }

  @Override
  public boolean testIgnoreCount(ItemStack stack) {
    return innerIngredient.testIgnoreCount(stack) && hasTrait(stack);
  }

  @Override
  public ItemStack consume(ItemStack input) {
    return innerIngredient.consume(input);
  }

  @Override
  public int getAmount() {
    return this.innerIngredient.getAmount();
  }

  private boolean hasTrait(ItemStack stack) {
    ICapabilityFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
    return cap != null && cap.getTraits().contains(trait);
  }
}
