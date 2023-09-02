package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.tree.TreeGeneratorHandler;
import net.dries007.tfc.api.types.wood.type.WoodTypeHandler;
import net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariantHandler;
import net.dries007.tfc.api.types.wood.variant.item.WoodItemVariantHandler;

public class WoodModule {

    public static void preInit() {
        TreeGeneratorHandler.init();
        WoodTypeHandler.init();
        WoodBlockVariantHandler.init();
        WoodItemVariantHandler.init();
    }
}
