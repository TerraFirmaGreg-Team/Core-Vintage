package net.dries007.tfc.module.wood.tree;

import net.dries007.tfc.module.wood.tree.type.TreeTypeHandler;

public class TreeModule {


    public static void preInit() {
        TreeTypeHandler.init();
    }

}
