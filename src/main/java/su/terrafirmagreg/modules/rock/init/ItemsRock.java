package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;
import su.terrafirmagreg.modules.rock.objects.items.ItemRockBrick;
import su.terrafirmagreg.modules.rock.objects.items.ItemRockGravel;
import su.terrafirmagreg.modules.rock.objects.items.ItemRockLoose;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class ItemsRock {

    public static final Map<Pair<RockItemVariant, RockType>, Item> ROCK_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    public static RockItemVariant LOOSE;
    public static RockItemVariant BRICK;
    public static RockItemVariant GRAVEL_LAYER;

    public static void onRegister(RegistryManager registry) {

        LOOSE = RockItemVariant
                .builder("loose")
                .setFactory(ItemRockLoose::new)
                .build();

        BRICK = RockItemVariant
                .builder("brick")
                .setFactory(ItemRockBrick::new)
                .build();

        GRAVEL_LAYER = RockItemVariant
                .builder("gravel_layer")
                .setFactory(ItemRockGravel::new)
                .build();

        registry.items(ROCK_ITEMS.values());
    }
}
