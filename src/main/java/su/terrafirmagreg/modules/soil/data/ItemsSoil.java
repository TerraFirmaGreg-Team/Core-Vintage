package su.terrafirmagreg.modules.soil.data;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

import net.minecraft.item.Item;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class ItemsSoil {

    public static final Map<Pair<SoilItemVariant, SoilType>, Item> SOIL_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    public static void onRegister(RegistryManager registry) {

        registry.registerItems(SOIL_ITEMS.values());
    }
}
