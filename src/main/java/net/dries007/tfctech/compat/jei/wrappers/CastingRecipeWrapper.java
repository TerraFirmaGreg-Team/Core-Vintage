package net.dries007.tfctech.compat.jei.wrappers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfctech.objects.items.ceramics.ItemTechMold;
import net.dries007.tfctech.objects.items.metal.ItemTechMetal;

public class CastingRecipeWrapper extends net.dries007.tfc.compat.jei.wrappers.CastingRecipeWrapper {

  private final ItemStack mold;
  private final FluidStack input;

  public CastingRecipeWrapper(Metal metal, ItemTechMetal.ItemType type) {
    super(metal, Metal.ItemType.INGOT); // Just so i can override
    input = new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144);
    mold = new ItemStack(ItemTechMold.get(type));
    IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    if (cap instanceof IMoldHandler) {
      cap.fill(input, true);
    }
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    ingredients.setInput(VanillaTypes.FLUID, input);
    ingredients.setOutput(VanillaTypes.ITEM, mold);
  }
}
