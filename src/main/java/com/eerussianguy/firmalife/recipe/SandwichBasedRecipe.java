package com.eerussianguy.firmalife.recipe;

import su.terrafirmagreg.api.capabilities.food.spi.FoodData;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;


import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.objects.recipes.ShapedDamageRecipe;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SandwichBasedRecipe extends ShapedDamageRecipe {

    public SandwichBasedRecipe(ResourceLocation group, CraftingHelper.ShapedPrimer input, @NotNull ItemStack result, int damage) {
        super(group, input, result, damage);
    }

    @Override
    public boolean matches(@NotNull InventoryCrafting inv, @NotNull World world) {
        if (super.matches(inv, world)) {
            List<FoodData> ingredients = new ArrayList<>();
            getIngredients(inv, ingredients);
            return ingredients.size() > 0;
        }
        return false;
    }

    protected void getIngredients(InventoryCrafting inv, List<FoodData> ingredients) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack ingredientStack = inv.getStackInSlot(i);
            IFood ingredientCap = ingredientStack.getCapability(CapabilityFood.CAPABILITY, null);
            if (ingredientCap != null) {
                if (ingredientCap.isRotten()) {
                    // Found a rotten ingredient, aborting
                    ingredients.clear();
                    return;
                }
                ingredients.add(ingredientCap.getData());
            }
        }
    }
}
