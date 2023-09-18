package net.dries007.tfc.module.crop;

import net.dries007.tfc.module.crop.api.category.CropCategoryHandler;
import net.dries007.tfc.module.crop.api.type.CropTypeHandler;
import net.dries007.tfc.module.crop.api.variant.block.CropBlockVariantHandler;
import net.dries007.tfc.module.crop.api.variant.item.CropItemVariantHandler;

public class CropModule {

    public static void preInit() {
        CropCategoryHandler.init();
        CropTypeHandler.init();
        CropBlockVariantHandler.init();
        CropItemVariantHandler.init();
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
