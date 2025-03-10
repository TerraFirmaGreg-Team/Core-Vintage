package net.dries007.tfc.api.recipes.heat;

import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HeatRecipeMetalMelting extends HeatRecipe {

  private final Metal metal; //Used only in JEI to determine the metal registered in this recipe.

  public HeatRecipeMetalMelting(Metal metal) {
    super(input -> {
      ICapabilityMetal metalObject = CapabilityMetal.get(input);
      if (metalObject != null) {
        return metalObject.getMetal(input) == metal;
      }
      return false;
    }, metal.getMeltTemp(), metal.getTier());
    this.metal = metal;
  }

  @Nullable
  @Override
  public FluidStack getOutputFluid(ItemStack input) {
    ICapabilityMetal metalObject = CapabilityMetal.get(input);
    if (metalObject != null) {
      Metal metal = metalObject.getMetal(input);
      if (metal != null) {
        if (metalObject.canMelt(input)) {
          return new FluidStack(FluidsTFC.getFluidFromMetal(metal), metalObject.getSmeltAmount(input));
        } else {
          // Melt into unknown alloy so items aren't simply voided and becomes something
          return new FluidStack(FluidsTFC.getFluidFromMetal(Metal.UNKNOWN), metalObject.getSmeltAmount(input));
        }
      }
    }
    return null;
  }

  //Used by JEI to determine valid inputs and the output
  public Metal getMetal() {
    return metal;
  }
}
