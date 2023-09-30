package net.dries007.tfc.api.recipes;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.metal.IMaterialItem;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.module.core.init.RegistryCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class BlastFurnaceRecipe extends IForgeRegistryEntry.Impl<BlastFurnaceRecipe> {
    protected Material output;
    protected Material input;
    protected IIngredient<ItemStack> additive;

    /**
     * Creates a new blast furnace recipe
     *
     * @param output   the metal output of this recipe
     * @param input    the metal input of this recipe
     * @param additive additive to make this recipe (for pig iron, this means flux)
     */
    public BlastFurnaceRecipe(Material output, Material input, IIngredient<ItemStack> additive) {
        this.output = output;
        this.input = input;
        this.additive = additive;

        //Ensure one blast furnace recipe per input metal
        //noinspection ConstantConditions
        setRegistryName(input.getName());
    }

    @Nullable
    public static BlastFurnaceRecipe get(ItemStack inputItem) {
        return RegistryCore.BLAST_FURNACE.getValuesCollection().stream().filter(x -> x.isValidInput(inputItem)).findFirst().orElse(null);
    }

    @Nullable
    public static BlastFurnaceRecipe get(Material inputMetal) {
        return RegistryCore.BLAST_FURNACE.getValuesCollection().stream().filter(x -> x.input == inputMetal).findFirst().orElse(null);
    }

    @Nullable
    public FluidStack getOutput(ItemStack stack) {
        IMaterialItem metal = CapabilityMetalItem.getMaterialItem(stack);
        int value = metal != null && metal.getMaterial(stack) == input ? metal.getSmeltAmount(stack) : 0;
        return value > 0 ? new FluidStack(output.getFluid(), value) : null;
    }

    public boolean isValidInput(ItemStack stack) {
        IMaterialItem metal = CapabilityMetalItem.getMaterialItem(stack);
        return metal != null && metal.getMaterial(stack) == input;
    }

    public boolean isValidAdditive(ItemStack stack) {
        return additive.testIgnoreCount(stack);
    }

    /**
     * For JEI only, gets an ingot of this recipe metal
     *
     * @return itemstack containing ingot of the specified metal
     */
    public ItemStack getOutput() {
        return OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.PigIron);
    }
}
