package net.dries007.tfc.module.food.api.variant.Item;

import net.dries007.tfc.module.food.common.items.ItemFoodIngredient;

import static net.dries007.tfc.module.food.api.variant.Item.FoodItemVariants.INGREDIENT;

public class FoodItemVariantHandler {

    public static void init() {

        INGREDIENT = new FoodItemVariant("ingredient", ItemFoodIngredient::new);
    }
}
