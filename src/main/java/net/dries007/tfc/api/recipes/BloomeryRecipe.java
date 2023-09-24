package net.dries007.tfc.api.recipes;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.metal.IMaterialItem;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BloomeryRecipe extends IForgeRegistryEntry.Impl<BloomeryRecipe> {
    private final Material metal; // Melting metal (which will be stored in a bloom)
    private final IIngredient<ItemStack> additive; // The additive used in the process (charcoal is the default for iron)

    public BloomeryRecipe(@Nonnull Material metal, IIngredient<ItemStack> additive) {
        this.metal = metal;
        this.additive = additive;

        //Ensure one bloomery recipe per metal
        //noinspection ConstantConditions
        setRegistryName(metal.getRegistryName());
    }

    @Nullable
    public static BloomeryRecipe get(@Nonnull ItemStack inputItem) {
        return TFCRegistries.BLOOMERY.getValuesCollection().stream().filter(x -> x.isValidInput(inputItem)).findFirst().orElse(null);
    }

    @Nullable
    public static BloomeryRecipe get(@Nonnull Material metal) {
        return TFCRegistries.BLOOMERY.getValuesCollection().stream().filter(x -> metal == x.metal).findFirst().orElse(null);
    }

    public ItemStack getOutput(List<ItemStack> inputs) {
        int metalAmount = 0;
        for (ItemStack stack : inputs) {
            IMaterialItem metalItem = CapabilityMetalItem.getMaterialItem(stack);
            if (metalItem != null) {
                metalAmount += metalItem.getSmeltAmount(stack);
            }
        }
        ItemStack bloom = new ItemStack(TFCItems.UNREFINED_BLOOM);
        IForgeable cap = bloom.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap instanceof IForgeableMeasurableMetal capBloom) {
            capBloom.setMetalAmount(metalAmount);
            capBloom.setMaterial(metal);
            capBloom.setTemperature(capBloom.getMeltTemp() - 1);
        }
        return bloom;
    }

    /**
     * Used in JEI, gets a bloom with 100 units
     *
     * @return Bloom itemstack containing 100 units
     */
    public ItemStack getOutput() {
        ItemStack bloom = new ItemStack(TFCItems.UNREFINED_BLOOM);
        IForgeable cap = bloom.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap instanceof IForgeableMeasurableMetal capBloom) {
            capBloom.setMetalAmount(100);
            capBloom.setMaterial(metal);
            capBloom.setTemperature(capBloom.getMeltTemp() - 1);
        }
        return bloom;
    }

    public boolean isValidInput(ItemStack inputItem) {
        var metalItem = CapabilityMetalItem.getMaterialItem(inputItem);
        return metalItem != null && metalItem.getMaterial(inputItem) == metal;
    }

    public boolean isValidAdditive(ItemStack input) {
        return additive.testIgnoreCount(input);
    }
}
