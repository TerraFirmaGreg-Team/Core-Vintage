package net.dries007.tfc.objects.recipes;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.IForgeRegistryEntry;


import com.google.gson.JsonObject;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")

public class SaltingRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private final NonNullList<Ingredient> input;
    private final ResourceLocation group;

    private SaltingRecipe(@Nullable ResourceLocation group, NonNullList<Ingredient> input) {
        this.group = group;
        this.input = input;
    }

    @Override
    public boolean matches(@NotNull InventoryCrafting inv, @NotNull World world) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                IFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
                if (food == null || (!food.isRotten() && !food.getTraits().contains(FoodTrait.SALTED))) {
                    items.add(stack);
                }
            }
        }
        return items.size() == input.size() && RecipeMatcher.findMatches(items, input) != null;
    }

    @Override
    @NotNull
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack result = ItemStack.EMPTY;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i).copy();
            stack.setCount(1);
            IFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
            if (food != null) {
                // Only apply salt to first food item found
                CapabilityFood.applyTrait(food, FoodTrait.SALTED);
                result = stack;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= input.size();
    }

    @Override
    @NotNull
    public ItemStack getRecipeOutput() {return ItemStack.EMPTY;}

    @Override
    @NotNull
    public NonNullList<Ingredient> getIngredients() {
        return this.input;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    @NotNull
    public String getGroup() {
        return this.group == null ? "" : this.group.toString();
    }

    public static class Factory implements IRecipeFactory {

        @Override
        public IRecipe parse(final JsonContext context, final JsonObject json) {
            final String group = JsonUtils.getString(json, "group", "");
            final NonNullList<Ingredient> ingredients = RecipeUtils.parseShapeless(context, json);
            return new SaltingRecipe(group.isEmpty() ? null : new ResourceLocation(group), ingredients);
        }
    }
}
