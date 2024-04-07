package tfcflorae.compat.tfcelementia.jei.wrappers;

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
import tfcelementia.objects.items.metal.ItemMetalTFCE;
import tfcflorae.compat.tfcelementia.ceramics.ItemKaoliniteMoldTFCE;

public class UnmoldRecipeKaoliniteTFCEWrapper implements IRecipeWrapper {

    private ItemStack mold;
    private ItemStack output;

    public UnmoldRecipeKaoliniteTFCEWrapper(Metal metal, ItemMetalTFCE.ItemType type) {
        mold = new ItemStack(ItemKaoliniteMoldTFCE.get(type));
        IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (cap instanceof IMoldHandler) {
            cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), type.getSmeltAmount()), true);
        }
        output = new ItemStack(ItemMetalTFCE.get(metal, type));
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, mold);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
