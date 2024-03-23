package su.terrafirmagreg.modules.metal.data;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariant;

import java.util.Map;


public final class BlocksMetal {

	public static final Map<Pair<MetalBlockVariant, MetalType>, Block> METAL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

	public static void onRegister(RegistryManager registry) {
		for (var block : METAL_BLOCKS.values()) registry.registerAuto(block);

	}

}
