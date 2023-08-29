package net.dries007.tfc.api.types.food;

import net.dries007.tfc.api.types.food.category.FoodCategoryHandler;
import net.dries007.tfc.api.types.food.type.FoodTypeHandler;

public class FoodModule {

    public static void preInit() {

        FoodCategoryHandler.init();
        FoodTypeHandler.init();
    }
}
