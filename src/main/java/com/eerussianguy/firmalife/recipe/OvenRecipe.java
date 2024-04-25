package com.eerussianguy.firmalife.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;


import com.eerussianguy.firmalife.init.RegistriesFL;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.compat.jei.IJEISimpleRecipe;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OvenRecipe extends IForgeRegistryEntry.Impl<OvenRecipe> implements IJEISimpleRecipe {

    private final int duration;
    protected IIngredient<ItemStack> inputItem;
    protected ItemStack outputItem;

    public OvenRecipe(IIngredient<ItemStack> input, ItemStack output, int duration) {
        this.inputItem = input;
        this.outputItem = output;
        this.duration = duration;

        if (inputItem == null || outputItem == null) {
            throw new IllegalArgumentException("Sorry, but you can't have oven recipes that don't have an input and output.");
        }
        if (duration < 1) {
            throw new IllegalArgumentException("Sorry, but oven recipes have to have a duration.");
        }
    }

    @Nullable
    public static OvenRecipe get(ItemStack item) {
        return RegistriesFL.OVEN.getValuesCollection()
                .stream()
                .filter(x -> x.isValidInput(item))
                .findFirst()
                .orElse(null);
    }

    public static int getDuration(OvenRecipe recipe) {
        return recipe.duration;
    }

    @NotNull
    public ItemStack getOutputItem(ItemStack stack) {
        return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());
    }

    // for JEI
    @Override
    public NonNullList<IIngredient<ItemStack>> getIngredients() {
        return NonNullList.withSize(1, inputItem);
    }

    @Override
    public NonNullList<ItemStack> getOutputs() {
        return NonNullList.withSize(1, outputItem);
    }

    private boolean isValidInput(ItemStack inputItem) {
        return this.inputItem.test(inputItem);
    }
}
