package net.dries007.tfc.compat.jei.wrappers;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.module.core.common.objects.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class UnmoldRecipeWrapper implements IRecipeWrapper {
    private final ItemStack mold;
    private final ItemStack output;

    public UnmoldRecipeWrapper(Material material, OrePrefix orePrefix) {
        mold = new ItemStack(TFCItems.FIRED_MOLDS.get(orePrefix));
        var cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (cap instanceof IMoldHandler) {
            cap.fill(new FluidStack(material.getFluid(), Helpers.getOrePrefixMaterialAmount(orePrefix)), true);
        }
        output = OreDictUnifier.get(orePrefix, material);
    }


    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, mold);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
