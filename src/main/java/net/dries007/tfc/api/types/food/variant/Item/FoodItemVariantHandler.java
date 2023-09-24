package net.dries007.tfc.api.types.food.variant.Item;

import net.dries007.tfc.common.objects.items.food.ItemFoodIngredient;

import static net.dries007.tfc.api.types.food.variant.Item.FoodItemVariants.INGREDIENT;

public class FoodItemVariantHandler {

    public static void init() {

        INGREDIENT = new FoodItemVariant("ingredient", ItemFoodIngredient::new);
    }
}
