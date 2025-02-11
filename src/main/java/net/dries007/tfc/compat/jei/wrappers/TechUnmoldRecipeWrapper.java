package net.dries007.tfc.compat.jei.wrappers;

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

public class TechUnmoldRecipeWrapper implements IRecipeWrapper {

  private final ItemStack mold;
  private final ItemStack output;

  private ItemTechMetal.ItemType type;
  private Metal metal;

  public TechUnmoldRecipeWrapper(Metal metal, ItemTechMetal.ItemType type) {
    mold = new ItemStack(ItemTechMold.get(type));

    this.type = type;
    this.metal = metal;

    IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    if (cap instanceof IMoldHandler) {
      cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144), true);
    }
    if (type == ItemTechMetal.ItemType.RACKWHEEL_PIECE) {
      switch (metal.toString()) {
        case "platinum":
        case "pig_iron":
        case "sterling_silver":
        case "tin":
        case "silver":
        case "rose_gold":
        case "lead":
        case "gold":
        case "copper":
        case "brass":
        case "bismuth_bronze": {
          output = ItemStack.EMPTY;
          break;
        }
        default: {
          //noinspection ConstantConditions
          output = new ItemStack(ItemTechMetal.get(metal, type));
        }
      }
    } else {
      //noinspection ConstantConditions
      output = new ItemStack(ItemTechMetal.get(metal, type));
    }
  }

  public TechUnmoldRecipeWrapper(ItemStack inputMold, ItemStack output) {
    this.mold = inputMold;
    this.output = output;
  }


  @Override
  public void getIngredients(IIngredients ingredients) {
    if (type == ItemTechMetal.ItemType.RACKWHEEL_PIECE) {
      switch (metal.toString()) {
        case "platinum":
        case "pig_iron":
        case "sterling_silver":
        case "tin":
        case "silver":
        case "rose_gold":
        case "lead":
        case "gold":
        case "copper":
        case "brass":
        case "bismuth_bronze": {
          break;
        }
        default: {
          ingredients.setInput(VanillaTypes.ITEM, mold);
          ingredients.setOutput(VanillaTypes.ITEM, output);
        }
      }
    } else {
      ingredients.setInput(VanillaTypes.ITEM, mold);
      ingredients.setOutput(VanillaTypes.ITEM, output);
    }
  }
}
