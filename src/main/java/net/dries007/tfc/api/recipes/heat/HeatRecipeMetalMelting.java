package net.dries007.tfc.api.recipes.heat;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.metal.IMaterialItem;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HeatRecipeMetalMelting extends HeatRecipe {
	private final Material material; // Used only in JEI to determine the metal registered in this recipe.

	public HeatRecipeMetalMelting(Material material) {
		super(input -> {
			var metalObject = CapabilityMetalItem.getMaterialItem(input);
			if (metalObject != null) {
				return metalObject.getMaterial(input) == material;
			}
			return false;
		}, material.getProperty(TFGPropertyKey.HEAT).getMeltTemp());
		this.material = material;
	}

	@Nullable
	@Override
	public FluidStack getOutputFluid(ItemStack input) {
		IMaterialItem metalObject = CapabilityMetalItem.getMaterialItem(input);
		if (metalObject != null) {
			var metal = metalObject.getMaterial(input);
			if (metal != null) {
				if (metalObject.canMelt(input)) {
					return new FluidStack(metal.getFluid(), metalObject.getSmeltAmount(input));
				} else {
					// Melt into unknown alloy so items aren't simply voided and becomes something
					return new FluidStack(TFGMaterials.Unknown.getFluid(), metalObject.getSmeltAmount(input));
				}
			}
		}
		return null;
	}

	// Used by JEI to determine valid inputs and the output
	public Material getMaterial() {
		return material;
	}
}