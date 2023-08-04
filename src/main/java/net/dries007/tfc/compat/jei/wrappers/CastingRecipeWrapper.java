package net.dries007.tfc.compat.jei.wrappers;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.util.Helpers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class CastingRecipeWrapper implements IRecipeWrapper {
	private final ItemStack mold;
	private final FluidStack input;

	public CastingRecipeWrapper(Material metal, OrePrefix orePrefix) {
		input = new FluidStack(metal.getFluid(), Helpers.getOrePrefixMaterialAmount(orePrefix));
		mold = new ItemStack(ItemMold.get(orePrefix));
		var cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
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
