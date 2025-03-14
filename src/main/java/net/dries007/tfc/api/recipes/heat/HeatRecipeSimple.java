package net.dries007.tfc.api.recipes.heat;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HeatRecipeSimple extends HeatRecipe {

  private final ItemStack output;
  private final float maxTemp;

  public HeatRecipeSimple(IIngredient<ItemStack> ingredient, ItemStack output, float transformTemp) {
    this(ingredient, output, transformTemp, Float.MAX_VALUE, Metal.Tier.TIER_0);
  }

  public HeatRecipeSimple(IIngredient<ItemStack> ingredient, ItemStack output, float transformTemp, float maxTemp) {
    this(ingredient, output, transformTemp, maxTemp, Metal.Tier.TIER_0);
  }

  public HeatRecipeSimple(IIngredient<ItemStack> ingredient, ItemStack output, float transformTemp, Metal.Tier minTier) {
    this(ingredient, output, transformTemp, Float.MAX_VALUE, minTier);
  }

  public HeatRecipeSimple(IIngredient<ItemStack> ingredient, ItemStack output, float transformTemp, float maxTemp, Metal.Tier minTier) {
    super(ingredient, transformTemp, minTier);
    this.output = output;
    this.maxTemp = maxTemp;
  }

  @Override
  @Nonnull
  public ItemStack getOutputStack(ItemStack input) {
    // No need to check min temp, as it would of already been matched in HeatRecipe
    ICapabilityHeat heat = input.getCapability(CapabilityHeat.CAPABILITY, null);
    if (heat != null && heat.getTemperature() <= maxTemp) {
      ItemStack outputStack = output.copy();
      ICapabilityHeat outputHeat = outputStack.getCapability(CapabilityHeat.CAPABILITY, null);
      if (outputHeat != null) {
        // Copy heat if possible
        outputHeat.setTemperature(heat.getTemperature());
      }
      return CapabilityFood.updateFoodFromPrevious(input, outputStack);
    }
    return ItemStack.EMPTY;
  }

  @Override
  public NonNullList<IIngredient<ItemStack>> getIngredients() {
    return NonNullList.withSize(1, this.ingredient);
  }

  @Override
  public NonNullList<ItemStack> getOutputs() {
    return NonNullList.withSize(1, output);
  }
}
