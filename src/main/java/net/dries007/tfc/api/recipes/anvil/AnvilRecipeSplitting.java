package net.dries007.tfc.api.recipes.anvil;

import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.forge.ICapabilityForge;
import su.terrafirmagreg.modules.core.capabilities.forge.IForgeableMeasurableMetal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.forge.ForgeRule;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This is an anvil recipe that will split an {@link IForgeableMeasurableMetal} into a specific "chunk" size Used by blooms to split a 560 -> 5x 100 blooms and
 * 1x 60 bloom for example
 */
@ParametersAreNonnullByDefault
public class AnvilRecipeSplitting extends AnvilRecipeMeasurable {

  protected int splitAmount;

  public AnvilRecipeSplitting(ResourceLocation name, IIngredient<ItemStack> input, ItemStack icon, int splitAmount, Metal.Tier minTier, ForgeRule... rules)
    throws IllegalArgumentException {
    //output only for icon
    super(name, input, icon, minTier, rules);
    this.splitAmount = splitAmount;
  }

  @Override
  public boolean matches(ItemStack input) {
    if (!super.matches(input)) {return false;}
    //Splitable if the output is at least two(don't change this or you will have duplicates)
    ICapabilityForge cap = input.getCapability(CapabilityForgeable.CAPABILITY, null);
    if (cap instanceof IForgeableMeasurableMetal) {return splitAmount < ((IForgeableMeasurableMetal) cap).getMetalAmount();}
    return false;
  }


  @Override
  @Nonnull
  public NonNullList<ItemStack> getOutput(ItemStack input) {
    if (matches(input)) {
      ICapabilityForge inCap = input.getCapability(CapabilityForgeable.CAPABILITY, null);
      if (inCap instanceof IForgeableMeasurableMetal) {
        int metalAmount = ((IForgeableMeasurableMetal) inCap).getMetalAmount();
        Metal metal = ((IForgeableMeasurableMetal) inCap).getMetal();
        int surplus = metalAmount % splitAmount;
        int outCount = metalAmount / splitAmount;

        NonNullList<ItemStack> output = NonNullList.create();
        for (int i = 0; i < outCount; i++) {
          ItemStack dump = input.copy();
          ICapabilityForge cap = dump.getCapability(CapabilityForgeable.CAPABILITY, null);
          if (cap instanceof IForgeableMeasurableMetal) {
            cap.reset();
            ((IForgeableMeasurableMetal) cap).setMetalAmount(splitAmount);
            ((IForgeableMeasurableMetal) cap).setMetal(metal);
          }
          output.add(dump);
        }
        if (surplus > 0) {
          ItemStack dumpSurplus = input.copy();
          ICapabilityForge cap = dumpSurplus.getCapability(CapabilityForgeable.CAPABILITY, null);
          if (cap instanceof IForgeableMeasurableMetal) {
            cap.reset();
            ((IForgeableMeasurableMetal) cap).setMetalAmount(surplus);
            ((IForgeableMeasurableMetal) cap).setMetal(metal);
          }

          output.add(dumpSurplus);
        }
        return output;
      }
    }
    return EMPTY;
  }
}
