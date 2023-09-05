package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.wood.type.WoodTypeHandler;
import net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariantHandler;
import net.dries007.tfc.api.types.wood.variant.item.WoodItemVariantHandler;

public class WoodModule {

    public static void preInit() {
        WoodTypeHandler.init();
        WoodBlockVariantHandler.init();
        WoodItemVariantHandler.init();
    }
}
