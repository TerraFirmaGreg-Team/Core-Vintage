package net.dries007.tfc.api.recipes;

import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.forge.ICapabilityForge;
import su.terrafirmagreg.modules.core.capabilities.forge.IForgeableMeasurableMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemsTFC;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BloomeryRecipe extends IForgeRegistryEntry.Impl<BloomeryRecipe> {

  private final Metal metal; // Melting metal (which will be stored in a bloom)
  private final IIngredient<ItemStack> additive; // The additive used in the process (charcoal is the default for iron)

  public BloomeryRecipe(@Nonnull Metal metal, IIngredient<ItemStack> additive) {
    this.metal = metal;
    this.additive = additive;

    //Ensure one bloomery recipe per metal
    //noinspection ConstantConditions
    setRegistryName(metal.getRegistryName());
  }

  @Nullable
  public static BloomeryRecipe get(@Nonnull ItemStack inputItem) {
    return TFCRegistries.BLOOMERY.getValuesCollection().stream().filter(x -> x.isValidInput(inputItem)).findFirst().orElse(null);
  }

  @Nullable
  public static BloomeryRecipe get(@Nonnull Metal metal) {
    return TFCRegistries.BLOOMERY.getValuesCollection().stream().filter(x -> metal == x.metal).findFirst().orElse(null);
  }

  public ItemStack getOutput(List<ItemStack> inputs) {
    int metalAmount = 0;
    for (ItemStack stack : inputs) {
      ICapabilityMetal metalItem = CapabilityMetal.getMetalItem(stack);
      if (metalItem != null) {
        metalAmount += metalItem.getSmeltAmount(stack);
      }
    }
    ItemStack bloom = new ItemStack(ItemsTFC.UNREFINED_BLOOM);
    ICapabilityForge cap = bloom.getCapability(CapabilityForgeable.CAPABILITY, null);
    if (cap instanceof IForgeableMeasurableMetal capBloom) {
      capBloom.setMetalAmount(metalAmount);
      capBloom.setMetal(metal);
      capBloom.setTemperature(capBloom.getMeltTemp() - 1);
    }
    return bloom;
  }

  /**
   * Used in JEI, gets a bloom with 100 units
   *
   * @return Bloom itemstack containing 100 units
   */
  public ItemStack getOutput() {
    ItemStack bloom = new ItemStack(ItemsTFC.UNREFINED_BLOOM);
    ICapabilityForge cap = bloom.getCapability(CapabilityForgeable.CAPABILITY, null);
    if (cap instanceof IForgeableMeasurableMetal capBloom) {
      capBloom.setMetalAmount(144);
      capBloom.setMetal(metal);
      capBloom.setTemperature(capBloom.getMeltTemp() - 1);
    }
    return bloom;
  }

  public boolean isValidInput(ItemStack inputItem) {
    ICapabilityMetal metalItem = CapabilityMetal.getMetalItem(inputItem);
    return metalItem != null && metalItem.getMetal(inputItem) == metal;
  }

  public boolean isValidAdditive(ItemStack input) {
    return additive.testIgnoreCount(input);
  }
}
