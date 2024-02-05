package su.terrafirmagreg.modules.rock;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.Pair;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.api.types.variant.item.IRockItem;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;

import java.util.Map;

public class StorageRock {

    public static final Map<Pair<RockBlockVariant, RockType>, IRockBlock> ROCK_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<Pair<RockItemVariant, RockType>, IRockItem> ROCK_ITEMS = new Object2ObjectLinkedOpenHashMap<>();


    @NotNull
    public static Block getBlock(@NotNull RockBlockVariant variant, @NotNull RockType type) {
        var block = (Block) ROCK_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block rock is null: %s, %s", variant, type));
    }

    @NotNull
    public static IRockBlock getIBlock(@NotNull RockBlockVariant variant, @NotNull RockType type) {
        var block = ROCK_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block rock is null: %s, %s", variant, type));
    }

    @NotNull
    public static Item getItem(@NotNull RockItemVariant variant, @NotNull RockType type) {
        var item = (Item) ROCK_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item rock is null: %s, %s", variant, type));
    }

    @NotNull
    public static IRockItem getIItem(@NotNull RockItemVariant variant, @NotNull RockType type) {
        var item = ROCK_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item rock is null: %s, %s", variant, type));
    }
}
