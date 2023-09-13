package net.dries007.tfc.api.types.food;

import net.dries007.tfc.api.types.food.category.FoodCategoryHandler;
import net.dries007.tfc.api.types.food.type.FoodTypeHandler;
import net.dries007.tfc.api.types.food.variant.Item.FoodItemVariantHandler;
import net.dries007.tfc.api.types.food.variant.block.FoodBlockVariantHandler;

public class FoodModule {

    public static void preInit() {

        FoodCategoryHandler.init();
        FoodTypeHandler.init();
        FoodBlockVariantHandler.init();
        FoodItemVariantHandler.init();
    }
}
