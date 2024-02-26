package su.terrafirmagreg.modules.rock.init;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;

import java.util.Map;

public final class ItemsRock {

	public static final Map<Pair<RockItemVariant, RockType>, Item> ROCK_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

	public static void onRegister(RegistryManager registry) {

		for (var item : ROCK_ITEMS.values()) registry.registerAuto(item);


	}


	@NotNull
	public static Item getItem(@NotNull RockItemVariant variant, @NotNull RockType type) {
		var item = (Item) ROCK_ITEMS.get(new Pair<>(variant, type));
		if (item != null) return item;
		throw new RuntimeException(String.format("Item rock is null: %s, %s", variant, type));
	}
}
