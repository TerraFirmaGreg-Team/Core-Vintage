package su.terrafirmagreg.modules.rock.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.item.Item;
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
}
