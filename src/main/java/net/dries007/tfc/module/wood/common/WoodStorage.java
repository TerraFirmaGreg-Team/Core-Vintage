package net.dries007.tfc.module.wood.common;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariant;
import net.dries007.tfc.module.wood.api.variant.item.IWoodItem;
import net.dries007.tfc.module.wood.api.variant.item.WoodItemVariant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Map;

public class WoodStorage {

    public static final Map<Pair<WoodBlockVariant, WoodType>, IWoodBlock> WOOD_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<Pair<WoodItemVariant, WoodType>, IWoodItem> WOOD_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    @Nonnull
    public static Block getWoodBlock(@Nonnull WoodBlockVariant variant, @Nonnull WoodType type) {
        var block = (Block) WOOD_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IWoodBlock getIWoodBlock(@Nonnull WoodBlockVariant variant, @Nonnull WoodType type) {
        var block = WOOD_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }


    @Nonnull
    public static Item getWoodItem(@Nonnull WoodItemVariant variant, @Nonnull WoodType type) {
        var item = (Item) WOOD_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IWoodItem getIWoodItem(@Nonnull WoodItemVariant variant, @Nonnull WoodType type) {
        var item = WOOD_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s, %s", variant, type));
    }
}
