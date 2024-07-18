package su.terrafirmagreg.modules.soil.init;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;
import su.terrafirmagreg.modules.soil.objects.items.ItemSoilMudBrick;
import su.terrafirmagreg.modules.soil.objects.items.ItemSoilMudWetBrick;
import su.terrafirmagreg.modules.soil.objects.items.ItemSoilPile;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class ItemsSoil {

    public static final Map<Pair<SoilItemVariant, SoilType>, Item> SOIL_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    public static SoilItemVariant PILE;
    public static SoilItemVariant MUD;
    public static SoilItemVariant MUD_BRICK;
    public static SoilItemVariant MUD_BRICK_WET;

    public static void onRegister(RegistryManager registry) {

        PILE = SoilItemVariant
                .builder("pile")
                .setFactory(ItemSoilPile::new)
                .build();

        MUD_BRICK = SoilItemVariant
                .builder("mud_brick")
                .setFactory(ItemSoilMudBrick::new)
                .build();

        MUD_BRICK_WET = SoilItemVariant
                .builder("mud_brick_wet")
                .setFactory(ItemSoilMudWetBrick::new)
                .build();

        registry.items(SOIL_ITEMS.values());
    }
}
