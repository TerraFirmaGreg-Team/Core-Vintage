package net.dries007.tfc.api.types.plant;

import net.dries007.tfc.api.types.plant.type.PlantTypeHandler;

public class PlantModule {

    public static void preInit() {

        //PlantCategoryHandler.init();
        PlantTypeHandler.init();
    }
}
