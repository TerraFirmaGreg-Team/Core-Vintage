package net.dries007.tfc.module.crop.api.category;

import net.minecraft.util.text.TextFormatting;

import static net.dries007.tfc.module.crop.api.category.CropCategories.PICKABLE;
import static net.dries007.tfc.module.crop.api.category.CropCategories.SIMPLE;

public class CropCategoryHandler {

    public static void init() {
        SIMPLE = new CropCategory("simple", TextFormatting.GREEN);
        PICKABLE = new CropCategory("pickable", TextFormatting.YELLOW);
    }
}
