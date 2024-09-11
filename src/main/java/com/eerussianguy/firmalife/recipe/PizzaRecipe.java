package com.eerussianguy.firmalife.recipe;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;


import com.google.gson.JsonObject;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.objects.items.ItemPizza;
import net.dries007.tfc.objects.recipes.RecipeUtils;
import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PizzaRecipe extends SandwichBasedRecipe {

  public PizzaRecipe(ResourceLocation group, CraftingHelper.ShapedPrimer input, @NotNull ItemStack result, int damage) {
    super(group, input, result, damage);
  }

  @NotNull
  @Override
  public ItemStack getCraftingResult(@NotNull InventoryCrafting inv) {
    ItemStack output = super.getCraftingResult(inv);
    IFood food = output.getCapability(CapabilityFood.CAPABILITY, null);
    if (food instanceof ItemPizza.PizzaHandler trailMix) {
      List<FoodData> ingredients = new ArrayList<>();
      getIngredients(inv, ingredients);
      if (ingredients.size() < 1) {
        return ItemStack.EMPTY;
      }

      trailMix.initCreationFoods(ingredients);
      trailMix.setCreationDate(
              Calendar.PLAYER_TIME.getTicks()); // Meals get decay reset as they have on average, high decay modifiers. Also it's too much of a pain to re-calculate a remaining decay fraction average
    }
    return output;
  }

  @SuppressWarnings("unused")
  public static class Factory implements IRecipeFactory {

    @Override
    public IRecipe parse(final JsonContext context, final JsonObject json) {
      String group = JsonUtils.getString(json, "group", "");

      CraftingHelper.ShapedPrimer primer = RecipeUtils.parsePhaped(context, json);

      ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
      final int damage;
      if (JsonUtils.hasField(json, "damage")) {
        damage = JsonUtils.getInt(json, "damage");
      } else {
        damage = 1;
      }
      return new PizzaRecipe(group.isEmpty() ? null : new ResourceLocation(group), primer, result, damage);
    }
  }
}
