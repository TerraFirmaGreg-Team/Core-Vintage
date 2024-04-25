package com.buuz135.hotornot.compat.jei.wrappers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


import com.buuz135.hotornot.object.item.HONItems;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.ItemType;
import net.dries007.tfc.compat.jei.wrappers.CastingRecipeWrapper;
import net.dries007.tfc.objects.fluids.FluidsTFC;

public class CastJawMoldRecipeWrapper extends CastingRecipeWrapper {

    private final FluidStack input;

    private final ItemStack output = new ItemStack(HONItems.TONGS_JAW_FIRED_MOLD);

    public CastJawMoldRecipeWrapper(final Metal metal) {
        // This is stupid but necessary unless we want to create our own recipe category just for the single casting recipe we have
        // as TFC casts the recipe wrapper to CastingRecipeWrapper. We override getIngredients, so it's just doing extra work
        // and using extra memory for no reason. Oh, well.
        super(metal, ItemType.INGOT);
        this.input = new FluidStack(FluidsTFC.getFluidFromMetal(metal), 100);
    }

    @Override
    public void getIngredients(final IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, input);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
