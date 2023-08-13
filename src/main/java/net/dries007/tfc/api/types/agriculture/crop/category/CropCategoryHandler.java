package net.dries007.tfc.api.types.agriculture.crop.category;

import net.dries007.tfc.api.types.food.category.FoodCategory;
import net.minecraft.util.text.TextFormatting;

import static net.dries007.tfc.api.types.agriculture.crop.category.CropCategories.*;
import static net.dries007.tfc.api.types.food.category.FoodCategories.*;

public class CropCategoryHandler {

    public static void init() {
        SIMPLE = new CropCategory("simple", TextFormatting.GREEN);
        PICKABLE = new CropCategory("pickable", TextFormatting.YELLOW);
        SPREADING = new CropCategory("spreading", TextFormatting.BLUE);
    }
}
