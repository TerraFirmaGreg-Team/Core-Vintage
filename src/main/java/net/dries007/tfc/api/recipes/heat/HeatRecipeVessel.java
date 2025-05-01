package net.dries007.tfc.api.recipes.heat;

import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ceramics.ItemSmallVessel;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HeatRecipeVessel extends HeatRecipe {

  public HeatRecipeVessel(IIngredient<ItemStack> ingredient, float transformTemp, Metal.Tier minTier) {
    super(ingredient, transformTemp, minTier);
  }

  @Override
  @Nonnull
  public ItemStack getOutputStack(ItemStack input) {
    ItemStack output = input.copy();
    if (output.getItem() instanceof ItemSmallVessel) {
      return ((ItemSmallVessel) output.getItem()).getFiringResult(output);
    }
    return output;
  }
}
