package net.dries007.tfc.module.metal;

import gregtech.api.unification.material.Material;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.metal.api.variant.Item.IMetalItem;
import net.dries007.tfc.module.metal.api.variant.Item.MetalItemVariant;
import net.dries007.tfc.module.metal.api.variant.block.IMetalBlock;
import net.dries007.tfc.module.metal.api.variant.block.MetalBlockVariant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Map;

public class StorageMetal {

    public static final Map<Pair<MetalBlockVariant, Material>, IMetalBlock> METAL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<Pair<MetalItemVariant, Material>, IMetalItem> METAL_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    @Nonnull
    public static Block getMetalBlock(@Nonnull MetalBlockVariant variant, @Nonnull Material material) {
        var block = (Block) METAL_BLOCKS.get(new Pair<>(variant, material));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block metal is null: %s, %s", variant, material));
    }

    @Nonnull
    public static IMetalBlock getIMetalBlock(@Nonnull MetalBlockVariant variant, @Nonnull Material material) {
        var block = METAL_BLOCKS.get(new Pair<>(variant, material));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block metal is null: %s, %s", variant, material));
    }

    @Nonnull
    public static Item getMetalItem(@Nonnull MetalItemVariant variant, @Nonnull Material type) {
        var item = (Item) METAL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item metal is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IMetalItem getIMetalItem(@Nonnull MetalItemVariant variant, @Nonnull Material type) {
        var item = METAL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item metal is null: %s, %s", variant, type));
    }
}
