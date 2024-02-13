package su.terrafirmagreg.modules.rock.init;

import net.minecraft.item.Item;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.Pair;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;

import java.util.Map;

public final class ItemsRock {

    public static final Map<Pair<RockItemVariant, RockType>, Item> ROCK_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    public static void onRegister(Registry registry) {

        for (var item : ROCK_ITEMS.values()) registry.registerAuto(item);


    }


    @NotNull
    public static Item getItem(@NotNull RockItemVariant variant, @NotNull RockType type) {
        var item = (Item) ROCK_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item rock is null: %s, %s", variant, type));
    }
}
