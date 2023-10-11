package net.dries007.tfc.module.metal;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.module.metal.api.types.type.MetalType;
import net.dries007.tfc.module.metal.api.types.variant.Item.IMetalItem;
import net.dries007.tfc.module.metal.api.types.variant.Item.MetalItemVariant;
import net.dries007.tfc.module.metal.api.types.variant.block.IMetalBlock;
import net.dries007.tfc.module.metal.api.types.variant.block.MetalBlockVariant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import su.terrafirmagreg.util.util.Pair;

import javax.annotation.Nonnull;
import java.util.Map;

public class StorageMetal {

    public static final Map<Pair<MetalBlockVariant, MetalType>, IMetalBlock> METAL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<Pair<MetalItemVariant, MetalType>, IMetalItem> METAL_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    @Nonnull
    public static Block getMetalBlock(@Nonnull MetalBlockVariant variant, @Nonnull MetalType material) {
        var block = (Block) METAL_BLOCKS.get(new Pair<>(variant, material));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block metal is null: %s, %s", variant, material));
    }

    @Nonnull
    public static IMetalBlock getIMetalBlock(@Nonnull MetalBlockVariant variant, @Nonnull MetalType material) {
        var block = METAL_BLOCKS.get(new Pair<>(variant, material));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block metal is null: %s, %s", variant, material));
    }

    @Nonnull
    public static Item getMetalItem(@Nonnull MetalItemVariant variant, @Nonnull MetalType type) {
        var item = (Item) METAL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item metal is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IMetalItem getIMetalItem(@Nonnull MetalItemVariant variant, @Nonnull MetalType type) {
        var item = METAL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item metal is null: %s, %s", variant, type));
    }
}
