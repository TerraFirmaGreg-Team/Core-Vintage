package net.dries007.tfc.module.core.submodule.rock.common;

import net.dries007.tfc.module.core.submodule.rock.api.type.RockType;
import net.dries007.tfc.module.core.submodule.rock.api.variant.block.IRockBlock;
import net.dries007.tfc.module.core.submodule.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.module.core.submodule.rock.api.variant.item.IRockItem;
import net.dries007.tfc.module.core.submodule.rock.api.variant.item.RockItemVariant;
import net.dries007.tfc.api.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

public class RockStorage {

    public static final Map<Pair<RockBlockVariant, RockType>, IRockBlock> ROCK_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<RockItemVariant, RockType>, IRockItem> ROCK_ITEMS = new LinkedHashMap<>();


    @Nonnull
    public static Block getRockBlock(@Nonnull RockBlockVariant variant, @Nonnull RockType type) {
        var block = (Block) ROCK_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IRockBlock getIRockBlock(@Nonnull RockBlockVariant variant, @Nonnull RockType type) {
        var block = ROCK_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Item getRockItem(@Nonnull RockItemVariant variant, @Nonnull RockType type) {
        var item = (Item) ROCK_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IRockItem getIRockItem(@Nonnull RockItemVariant variant, @Nonnull RockType type) {
        var item = ROCK_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s, %s", variant, type));
    }
}
