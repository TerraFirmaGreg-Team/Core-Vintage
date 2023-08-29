package net.dries007.tfc.api.types.bush;

import net.dries007.tfc.api.types.bush.type.BushTypeHandler;

public class BushModule {

    public static void preInit() {

        BushTypeHandler.init();
    }
}
