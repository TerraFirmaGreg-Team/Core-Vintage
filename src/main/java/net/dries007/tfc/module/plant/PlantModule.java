package net.dries007.tfc.module.plant;

import net.dries007.tfc.module.plant.api.category.PlantCategoryHandler;
import net.dries007.tfc.module.plant.api.type.PlantTypeHandler;
import net.dries007.tfc.module.plant.api.variant.block.PlantBlockVariantHandler;
import net.dries007.tfc.module.plant.api.variant.item.PlantItemVariantHandler;

public class PlantModule {

    public static void preInit() {
        PlantCategoryHandler.init();
        PlantTypeHandler.init();
        PlantBlockVariantHandler.init();
        PlantItemVariantHandler.init();
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
