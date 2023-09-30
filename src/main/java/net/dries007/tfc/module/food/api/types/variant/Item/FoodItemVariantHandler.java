package net.dries007.tfc.module.food.api.types.variant.Item;

import net.dries007.tfc.module.food.objects.items.ItemFoodIngredient;

import static net.dries007.tfc.module.food.api.types.variant.Item.FoodItemVariants.INGREDIENT;

public class FoodItemVariantHandler {

    public static void init() {

        INGREDIENT = new FoodItemVariant("ingredient", ItemFoodIngredient::new);
    }
}
