package tfctech.compat.jei.wrappers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;


import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemTechMold;
import net.dries007.tfc.objects.items.metal.ItemTechMetal;

public class UnmoldRecipeWrapper implements IRecipeWrapper {

  private final ItemStack mold;
  private final ItemStack output;

  public UnmoldRecipeWrapper(Metal metal, ItemTechMetal.ItemType type) {
    mold = new ItemStack(ItemTechMold.get(type));
    IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    if (cap instanceof IMoldHandler) {
      cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 100), true);
    }
    //noinspection ConstantConditions
    output = new ItemStack(ItemTechMetal.get(metal, type));
  }

  public UnmoldRecipeWrapper(ItemStack inputMold, ItemStack output) {
    this.mold = inputMold;
    this.output = output;
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    ingredients.setInput(VanillaTypes.ITEM, mold);
    ingredients.setOutput(VanillaTypes.ITEM, output);
  }
}
