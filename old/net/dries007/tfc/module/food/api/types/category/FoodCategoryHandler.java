package net.dries007.tfc.module.food.api.types.category;

import net.minecraft.util.text.TextFormatting;

import static net.dries007.tfc.module.food.api.types.category.FoodCategories.*;

public class FoodCategoryHandler {

    public static void init() {
        FoodCategories.FRUIT = new FoodCategory("fruit", TextFormatting.GREEN);
        FoodCategories.GRAIN = new FoodCategory("grain", TextFormatting.YELLOW);
        FoodCategories.BREAD = new FoodCategory("bread", TextFormatting.GOLD);
        FoodCategories.VEGETABLE = new FoodCategory("vegetable", TextFormatting.GRAY);
        FoodCategories.MEAT = new FoodCategory("meat", TextFormatting.RED);
        FoodCategories.COOKED_MEAT = new FoodCategory("cooked_meat", TextFormatting.RED);
        FoodCategories.DAIRY = new FoodCategory("dairy", TextFormatting.WHITE);
        FoodCategories.MEAL = new FoodCategory("meal", TextFormatting.AQUA);
        FoodCategories.OTHER = new FoodCategory("other", TextFormatting.DARK_GRAY);
    }
}
