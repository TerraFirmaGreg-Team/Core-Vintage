package su.terrafirmagreg.modules.metal.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.item.Item;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.Item.MetalItemVariant;

import java.util.Map;


public class ItemsMetal {

	public static final Map<Pair<MetalItemVariant, MetalType>, Item> METAL_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

	public static void onRegister(RegistryManager registry) {

		for (var item : METAL_ITEMS.values()) registry.registerAuto(item);


	}

}
