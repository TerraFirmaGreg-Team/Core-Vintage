package su.terrafirmagreg.modules.wood.data;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.item.Item;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

import java.util.Map;

public final class ItemsWood {

	public static final Map<Pair<WoodItemVariant, WoodType>, Item> WOOD_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

	public static void onRegister(RegistryManager registry) {
		for (var item : WOOD_ITEMS.values()) registry.registerAuto(item);

		//for (var item : TREE_ITEMS) registry.registerItem(item);
	}
}
