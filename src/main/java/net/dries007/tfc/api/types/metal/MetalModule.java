package net.dries007.tfc.api.types.metal;

import net.dries007.tfc.api.types.metal.variant.block.MetalBlockVariantHandler;

public class MetalModule {

    public static void preInit() {

        MetalBlockVariantHandler.init();
    }
}
