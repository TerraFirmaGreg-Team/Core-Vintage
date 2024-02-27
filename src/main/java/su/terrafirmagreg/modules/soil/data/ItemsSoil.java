package su.terrafirmagreg.modules.soil.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

import java.util.Map;

public final class ItemsSoil {

	public static final Map<Pair<SoilItemVariant, SoilType>, Item> SOIL_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

	public static void onRegister(RegistryManager registry) {

		for (var item : SOIL_ITEMS.values()) registry.registerAuto(item);
	}


	@NotNull
	public static Item getItem(@NotNull SoilItemVariant variant, @NotNull SoilType type) {
		var item = (Item) SOIL_ITEMS.get(new Pair<>(variant, type));
		if (item != null) return item;
		throw new RuntimeException(String.format("Item soil is null: %s, %s", variant, type));
	}
}
