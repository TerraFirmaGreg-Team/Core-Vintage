package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodMisc;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import java.util.Map;

public final class ItemsWood {

    public static final Map<Pair<WoodItemVariant, WoodType>, Item> WOOD_ITEMS = new Object2ObjectLinkedOpenHashMap<>();
    public static ItemWoodMisc STICK_BUNDLE;
    public static ItemWoodMisc STICK_BUNCH;

    public static void onRegister(RegistryManager registry) {
        registry.items(WOOD_ITEMS.values());

        STICK_BUNDLE = registry.item(new ItemWoodMisc("stick_bundle", Size.VERY_LARGE, Weight.MEDIUM, "log_wood", "stick_bundle"));
        STICK_BUNCH = registry.item(new ItemWoodMisc("stick_bunch", Size.NORMAL, Weight.LIGHT));
    }
}
