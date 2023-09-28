package net.dries007.tfc.module.food;

import net.dries007.tfc.module.food.api.category.FoodCategoryHandler;
import net.dries007.tfc.module.food.api.type.FoodTypeHandler;
import net.dries007.tfc.module.food.api.variant.Item.FoodItemVariantHandler;
import net.dries007.tfc.module.food.api.variant.block.FoodBlockVariantHandler;

public class ModuleFood {

    public static void preInit() {
        FoodCategoryHandler.init();
        FoodTypeHandler.init();
        FoodBlockVariantHandler.init();
        FoodItemVariantHandler.init();
    }
}
