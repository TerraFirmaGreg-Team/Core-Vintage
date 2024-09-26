package net.dries007.tfc.api.recipes.anvil;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.forge.ForgeRule;

import org.jetbrains.annotations.NotNull;

/**
 * AnvilRecipe implementation for copying {@link IForgeableMeasurableMetal} cap from one item to another
 */

public class AnvilRecipeMeasurable extends AnvilRecipe {

  /**
   * Creates a new recipe that copies {@link IForgeableMeasurableMetal} capability from one item to another (both must implement it)
   *
   * @param input   the predicate for input.
   * @param output  the output stack
   * @param minTier the anvil's min tier required to work this recipe
   * @param rules   the forging rules
   */
  public AnvilRecipeMeasurable(ResourceLocation name, IIngredient<ItemStack> input, ItemStack output, Metal.Tier minTier, ForgeRule... rules)
    throws IllegalArgumentException {
    super(name, input, output, minTier, null, rules);
  }

  @Override
  @NotNull
  public NonNullList<ItemStack> getOutputItem(ItemStack input) {
    if (matches(input)) {
      NonNullList<ItemStack> out = super.getOutputItem(input);
      IForgeable inputCap = input.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
      IForgeable outputCap = out.get(0).getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
      if (inputCap instanceof IForgeableMeasurableMetal && outputCap instanceof IForgeableMeasurableMetal) {
        ((IForgeableMeasurableMetal) outputCap).setMetalAmount(((IForgeableMeasurableMetal) inputCap).getMetalAmount());
        ((IForgeableMeasurableMetal) outputCap).setMetal(((IForgeableMeasurableMetal) inputCap).getMetal());
      }
      return out;
    }
    return EMPTY;
  }
}
