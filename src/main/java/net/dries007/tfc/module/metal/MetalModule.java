package net.dries007.tfc.module.metal;

import net.dries007.tfc.api.util.TFCCreativeTab;
import net.dries007.tfc.module.metal.api.variant.Item.MetalItemVariantHandler;
import net.dries007.tfc.module.metal.api.variant.block.MetalBlockVariantHandler;
import net.minecraft.creativetab.CreativeTabs;

public class MetalModule {

    public static final CreativeTabs METAL_TAB = new TFCCreativeTab("metal", "tfc:metal/anvil/red_steel");

    public static void preInit() {
        MetalBlockVariantHandler.init();
        MetalItemVariantHandler.init();
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
