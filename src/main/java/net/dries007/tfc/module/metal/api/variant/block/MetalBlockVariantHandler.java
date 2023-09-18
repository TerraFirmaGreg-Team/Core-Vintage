package net.dries007.tfc.module.metal.api.variant.block;

import net.dries007.tfc.module.metal.common.blocks.BlockMetalAnvil;

import static net.dries007.tfc.module.metal.api.variant.block.MetalBlockVariants.ANVIL;

public class MetalBlockVariantHandler {

    public static void init() {
        ANVIL = new MetalBlockVariant("anvil", BlockMetalAnvil::new);
    }
}
