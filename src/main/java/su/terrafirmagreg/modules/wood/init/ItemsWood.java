package su.terrafirmagreg.modules.wood.init;


import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.api.util.Pair;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

import java.util.Map;

public final class ItemsWood {

    public static final Map<Pair<WoodItemVariant, WoodType>, Item> WOOD_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    public static void onRegister(Registry registry) {
        for (var item : WOOD_ITEMS.values()) registry.registerAuto(item);

        //for (var item : TREE_ITEMS) registry.registerItem(item);
    }


    @NotNull
    public static Item getItem(@NotNull WoodItemVariant variant, @NotNull WoodType type) {
        var item = (Item) WOOD_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item wood is null: %s, %s", variant, type));
    }
}
