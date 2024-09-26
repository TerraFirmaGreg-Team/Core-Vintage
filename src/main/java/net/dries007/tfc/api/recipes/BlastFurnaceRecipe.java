package net.dries007.tfc.api.recipes;

import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.metal.ItemIngot;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("WeakerAccess")
public class BlastFurnaceRecipe extends IForgeRegistryEntry.Impl<BlastFurnaceRecipe> {

  protected Metal output;
  protected Metal input;
  protected IIngredient<ItemStack> additive;

  /**
   * Creates a new blast furnace recipe
   *
   * @param output   the metal output of this recipe
   * @param input    the metal input of this recipe
   * @param additive additive to make this recipe (for pig iron, this means flux)
   */
  public BlastFurnaceRecipe(Metal output, Metal input, IIngredient<ItemStack> additive) {
    this.output = output;
    this.input = input;
    this.additive = additive;

    //Ensure one blast furnace recipe per input metal
    //noinspection ConstantConditions
    setRegistryName(input.getRegistryName());
  }

  @Nullable
  public static BlastFurnaceRecipe get(ItemStack inputItem) {
    return TFCRegistries.BLAST_FURNACE.getValuesCollection()
                                      .stream()
                                      .filter(x -> x.isValidInput(inputItem))
                                      .findFirst()
                                      .orElse(null);
  }

  @Nullable
  public static BlastFurnaceRecipe get(Metal inputMetal) {
    return TFCRegistries.BLAST_FURNACE.getValuesCollection()
                                      .stream()
                                      .filter(x -> x.input == inputMetal)
                                      .findFirst()
                                      .orElse(null);
  }

  public boolean isValidInput(ItemStack stack) {
    var metal = CapabilityMetal.getMetalItem(stack);
    return metal != null && metal.getMetal(stack) == input;
  }

  @Nullable
  public FluidStack getOutput(ItemStack stack) {
    var metal = CapabilityMetal.getMetalItem(stack);
    int value = metal != null && metal.getMetal(stack) == input ? metal.getSmeltAmount(stack) : 0;
    return value > 0 ? new FluidStack(FluidsTFC.getFluidFromMetal(output), value) : null;
  }

  public boolean isValidAdditive(ItemStack stack) {
    return additive.testIgnoreCount(stack);
  }

  /**
   * For JEI only, gets an ingot of this recipe metal
   *
   * @return itemstack containing ingot of the specified metal
   */
  public ItemStack getOutput() {
    return new ItemStack(ItemIngot.get(output, Metal.ItemType.INGOT));
  }
}
