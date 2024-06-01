package su.terrafirmagreg.modules.food.init;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public class ItemsFood {

    public static final Map<Pair<RockItemVariant, RockType>, Item> FOOD_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    public static void onRegister(RegistryManager registry) {

    }

}
