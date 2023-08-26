package net.dries007.tfc.api.types.metal.variant.block;

import net.dries007.tfc.common.objects.blocks.metal.BlockMetalAnvil;

import static net.dries007.tfc.api.types.metal.variant.block.MetalBlockVariants.ANVIL;

public class MetalBlockVariantHandler {

    public static void init() {
        ANVIL = new MetalBlockVariant("anvil", BlockMetalAnvil::new);
    }
}
