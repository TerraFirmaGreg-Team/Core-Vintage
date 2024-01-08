package com.buuz135.hotornot.compat.jei.wrappers;

import com.buuz135.hotornot.object.item.HONItems;
import com.buuz135.hotornot.object.item.ItemMetalTongsHead;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class UnMoldJawPieceRecipeWrapper implements IRecipeWrapper {

	private final ItemStack input = new ItemStack(HONItems.TONGS_JAW_FIRED_MOLD);

	private final ItemStack output;

	public UnMoldJawPieceRecipeWrapper(final Metal metal) {
		final IFluidHandler fluidHandler = input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		if (fluidHandler instanceof IMoldHandler) {
			fluidHandler.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 100), true);
		}

		this.output = new ItemStack(ItemMetalTongsHead.get(metal));
	}

	@Override
	public void getIngredients(final IIngredients iIngredients) {
		iIngredients.setInput(VanillaTypes.ITEM, input);
		iIngredients.setOutput(VanillaTypes.ITEM, output);
	}
}
