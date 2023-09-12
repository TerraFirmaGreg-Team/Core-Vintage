package net.dries007.tfc.api.types.tree;

import net.dries007.tfc.api.types.tree.type_new.TreeTypeHandler;

public class TreeModule {

    public static void preInit() {
        TreeTypeHandler.init();
    }
}
