package net.dries007.tfc.api.types.metal.variant;

import net.dries007.tfc.objects.blocks.metal.BlockMetalAnvil;

import static net.dries007.tfc.api.types.metal.variant.MetalBlockVariants.*;

public class MetalBlockVariantHandler {

    public static void init() {
        ANVIL = new MetalBlockVariant("anvil", BlockMetalAnvil::new);
    }
}
