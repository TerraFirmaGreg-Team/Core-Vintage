package net.dries007.tfc.module.metal;

import net.dries007.tfc.module.core.common.objects.TFCCreativeTab;
import net.dries007.tfc.module.metal.api.variant.Item.MetalItemVariantHandler;
import net.dries007.tfc.module.metal.api.variant.block.MetalBlockVariantHandler;
import net.dries007.tfc.module.metal.common.MetalStorage;
import net.minecraft.creativetab.CreativeTabs;

import static gregtech.api.unification.material.Materials.RedSteel;
import static net.dries007.tfc.module.metal.api.variant.block.MetalBlockVariants.ANVIL;

public class MetalModule {

    public static final CreativeTabs METAL_TAB = new TFCCreativeTab("metal", MetalStorage.getMetalBlock(ANVIL, RedSteel));

    public static void preInit() {
        MetalBlockVariantHandler.init();
        MetalItemVariantHandler.init();
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
