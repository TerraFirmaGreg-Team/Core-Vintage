package su.terrafirmagreg.modules.food.api.types.category;

import net.minecraft.util.text.TextFormatting;


import static su.terrafirmagreg.modules.food.api.types.category.FoodCategories.*;

public final class FoodCategoryHandler {

    public static void init() {

        FRUIT = new FoodCategory
                .Builder("fruit")
                .setTextFormatting(TextFormatting.GREEN)
                .build();

        GRAIN = new FoodCategory
                .Builder("grain")
                .setTextFormatting(TextFormatting.YELLOW)
                .build();

        BREAD = new FoodCategory
                .Builder("bread")
                .setTextFormatting(TextFormatting.GOLD)
                .build();

        VEGETABLE = new FoodCategory
                .Builder("vegetable")
                .setTextFormatting(TextFormatting.GRAY)
                .build();

        MEAT = new FoodCategory
                .Builder("meat")
                .setTextFormatting(TextFormatting.RED)
                .build();

        COOKED_MEAT = new FoodCategory
                .Builder("cooked_meat")
                .setTextFormatting(TextFormatting.RED)
                .build();

        DAIRY = new FoodCategory
                .Builder("dairy")
                .setTextFormatting(TextFormatting.WHITE)
                .build();

        MEAL = new FoodCategory
                .Builder("meal")
                .setTextFormatting(TextFormatting.AQUA)
                .build();

        OTHER = new FoodCategory
                .Builder("other")
                .setTextFormatting(TextFormatting.DARK_GRAY)
                .build();

    }
}
