package net.dries007.tfc.module.wood;

import net.dries007.tfc.module.wood.api.type.WoodTypeHandler;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariantHandler;
import net.dries007.tfc.module.wood.api.variant.item.WoodItemVariantHandler;

public class ModuleWood {

    public static void preInit() {
        WoodTypeHandler.init();
        WoodBlockVariantHandler.init();
        WoodItemVariantHandler.init();
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
