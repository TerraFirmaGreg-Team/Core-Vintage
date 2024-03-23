package su.terrafirmagreg.modules.metal.api.types.variant.block;


import su.terrafirmagreg.modules.metal.objects.blocks.BlockMetalAnvil;
import su.terrafirmagreg.modules.metal.objects.blocks.BlockMetalCladding;

import static su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariants.ANVIL;
import static su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariants.CLADDING;

public class MetalBlockVariantHandler {

	public static void init() {

		ANVIL = new MetalBlockVariant.Builder("anvil")
				.setFactory(BlockMetalAnvil::new)
				.build();

		CLADDING = new MetalBlockVariant.Builder("cladding")
				.setFactory(BlockMetalCladding::new)
				.build();
	}
}
