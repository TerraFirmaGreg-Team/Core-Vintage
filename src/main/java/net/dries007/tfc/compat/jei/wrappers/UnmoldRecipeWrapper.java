package net.dries007.tfc.compat.jei.wrappers;

import su.terrafirmagreg.temp.util.TFGModUtils;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import gregtech.api.unification.OreDictUnifier;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.metal.ItemMetal;

public class UnmoldRecipeWrapper implements IRecipeWrapper {

  // todo in 1.15, refactor UnmoldRecipe and split the json recipe file to the metal and mold permutation.
  // Can't do like SaltingRecipeWrapper because that makes the ingredient and output rotation weird (ie: copper ingot output in brass filled mold)
  private final ItemStack mold;
  private final ItemStack output;

  public UnmoldRecipeWrapper(Metal metal, Metal.ItemType type) {
    mold = new ItemStack(ItemMold.get(type));
    IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    if (cap instanceof IMoldHandler) {
      cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144), true);
    }
    String oreDict = TFGModUtils.constructOredictFromTFCToGT(metal, type);
    ItemStack outputTest = OreDictUnifier.get(oreDict);

    if (!outputTest.getItem().equals(Items.AIR)) {
      this.output = outputTest;
    } else {
      this.output = new ItemStack(ItemMetal.get(metal, type));
    }
  }


  @Override
  public void getIngredients(IIngredients ingredients) {
    ingredients.setInput(VanillaTypes.ITEM, mold);
    ingredients.setOutput(VanillaTypes.ITEM, output);
  }
}
