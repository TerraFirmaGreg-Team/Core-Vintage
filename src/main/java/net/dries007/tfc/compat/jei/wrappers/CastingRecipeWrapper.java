/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.jei.wrappers;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CastingRecipeWrapper implements IRecipeWrapper
{
    private ItemStack mold;
    private FluidStack input;

    public CastingRecipeWrapper(Metal metal, Metal.ItemType type)
    {
        /*
        input = new FluidStack(FluidsTFC.getFluidFromMetal(metal), 100);
        mold = new ItemStack(ItemMold.get(type));
        IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (cap instanceof IMoldHandler)
        {
            cap.fill(input, true);
        }*/
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(VanillaTypes.FLUID, input);
        ingredients.setOutput(VanillaTypes.ITEM, mold);
    }
}
