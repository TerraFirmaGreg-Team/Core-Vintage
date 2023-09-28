package net.dries007.tfc.module.rock;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.dries007.tfc.module.rock.api.types.variant.block.IRockBlock;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariant;
import net.dries007.tfc.module.rock.api.types.variant.item.IRockItem;
import net.dries007.tfc.module.rock.api.types.variant.item.RockItemVariant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Map;

public class StorageRock {

    public static final Map<Pair<RockBlockVariant, RockType>, IRockBlock> ROCK_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<Pair<RockItemVariant, RockType>, IRockItem> ROCK_ITEMS = new Object2ObjectLinkedOpenHashMap<>();


    @Nonnull
    public static Block getRockBlock(@Nonnull RockBlockVariant variant, @Nonnull RockType type) {
        var block = (Block) ROCK_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block rock is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IRockBlock getIRockBlock(@Nonnull RockBlockVariant variant, @Nonnull RockType type) {
        var block = ROCK_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block rock is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Item getRockItem(@Nonnull RockItemVariant variant, @Nonnull RockType type) {
        var item = (Item) ROCK_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item rock is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IRockItem getIRockItem(@Nonnull RockItemVariant variant, @Nonnull RockType type) {
        var item = ROCK_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item rock is null: %s, %s", variant, type));
    }
}
