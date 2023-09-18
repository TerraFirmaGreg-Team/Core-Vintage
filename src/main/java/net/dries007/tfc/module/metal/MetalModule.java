package net.dries007.tfc.module.metal;

import net.dries007.tfc.module.metal.api.variant.Item.MetalItemVariantHandler;
import net.dries007.tfc.module.metal.api.variant.block.MetalBlockVariantHandler;

public class MetalModule {

    public static void preInit() {
        MetalBlockVariantHandler.init();
        MetalItemVariantHandler.init();
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
