package net.dries007.tfc.module.metal.api.types.variant.block;

import net.dries007.tfc.module.metal.objects.blocks.BlockMetalAnvil;
import net.dries007.tfc.module.metal.objects.blocks.BlockMetalCladding;

import static net.dries007.tfc.module.metal.api.types.variant.block.MetalBlockVariants.ANVIL;
import static net.dries007.tfc.module.metal.api.types.variant.block.MetalBlockVariants.CLADDING;

public class MetalBlockVariantHandler {

    public static void init() {

        ANVIL = new MetalBlockVariant("anvil", BlockMetalAnvil::new);
        CLADDING = new MetalBlockVariant("cladding", BlockMetalCladding::new);
    }
}
