package com.eerussianguy.firmalife.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;


import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrackingRecipe extends IForgeRegistryEntry.Impl<CrackingRecipe> {

    protected IIngredient<ItemStack> inputItem;
    protected ItemStack outputItem;
    protected float chance;

    public CrackingRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem, float chance) {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.chance = chance;

        if (inputItem == null || outputItem == null)
            throw new IllegalArgumentException("Sorry, but you can't have cracking recipes that don't have an input and output");
        if (chance < 0 || chance > 1)
            throw new IllegalArgumentException("Sorry, the chance to drop must be between 0 and 1");
    }

    @Nullable
    public static CrackingRecipe get(ItemStack item) {
        return TFCRegistries.CRACKING.getValuesCollection()
                .stream()
                .filter(x -> x.isValidInput(item))
                .findFirst()
                .orElse(null);
    }

    @NotNull
    public int getChance() {
        return (int) (chance * 100);
    }

    public ItemStack getOutputItem(ItemStack stack) {return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());}

    private boolean isValidInput(ItemStack inputItem) {return this.inputItem.test(inputItem);}
}


