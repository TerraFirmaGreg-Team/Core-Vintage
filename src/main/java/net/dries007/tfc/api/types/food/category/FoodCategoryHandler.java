package net.dries007.tfc.api.types.food.category;

import net.minecraft.util.text.TextFormatting;

import static net.dries007.tfc.api.types.food.category.FoodCategories.*;

public class FoodCategoryHandler {

    public static void init() {
        FRUIT = new FoodCategory("fruit", TextFormatting.GREEN);
        GRAIN = new FoodCategory("grain", TextFormatting.YELLOW);
        BREAD = new FoodCategory("bread", TextFormatting.GOLD);
        VEGETABLE = new FoodCategory("vegetable", TextFormatting.GRAY);
        MEAT = new FoodCategory("meat", TextFormatting.RED);
        COOKED_MEAT = new FoodCategory("cooked_meat", TextFormatting.RED);
        DAIRY = new FoodCategory("dairy", TextFormatting.WHITE);
        MEAL = new FoodCategory("meal", TextFormatting.GRAY);
        OTHER = new FoodCategory("other", TextFormatting.GRAY);
    }
}
