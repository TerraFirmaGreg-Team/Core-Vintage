package net.dries007.tfc.api.types.tree;

import net.dries007.tfc.api.types.tree.type.TreeTypeHandler;
import net.dries007.tfc.api.types.tree.variant.block.TreeBlockVariantHandler;
import net.dries007.tfc.api.types.tree.variant.item.TreeItemVariantHandler;

public class TreeModule {

    public static void preInit() {
        TreeTypeHandler.init();
        TreeBlockVariantHandler.init();
        TreeItemVariantHandler.init();
    }
}
