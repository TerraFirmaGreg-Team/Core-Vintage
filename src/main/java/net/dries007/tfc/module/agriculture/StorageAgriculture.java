package net.dries007.tfc.module.agriculture;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.agriculture.api.types.bush.IBushBlock;
import net.dries007.tfc.module.agriculture.api.types.bush.type.BushType;
import net.dries007.tfc.module.agriculture.api.types.crop.type.CropType;
import net.dries007.tfc.module.agriculture.api.types.crop.variant.block.CropBlockVariant;
import net.dries007.tfc.module.agriculture.api.types.crop.variant.block.ICropBlock;
import net.dries007.tfc.module.agriculture.api.types.crop.variant.item.CropItemVariant;
import net.dries007.tfc.module.agriculture.api.types.crop.variant.item.ICropItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Map;

public class StorageAgriculture {

    public static final Map<Pair<CropBlockVariant, CropType>, ICropBlock> CROP_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<BushType, IBushBlock> BUSH_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

    public static final Map<Pair<CropItemVariant, CropType>, ICropItem> CROP_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    @Nonnull
    public static Block getCropBlock(@Nonnull CropBlockVariant variant, @Nonnull CropType type) {
        var block = (Block) CROP_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block crop is null: %s, %s", variant, type));
    }

    @Nonnull
    public static ICropBlock getICropBlock(@Nonnull CropBlockVariant variant, @Nonnull CropType type) {
        var block = CROP_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block crop is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Block getBushBlock(@Nonnull BushType type) {
        var block = (Block) BUSH_BLOCKS.get(type);
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s", type));
    }


    @Nonnull
    public static Item getCropItem(@Nonnull CropItemVariant variant, @Nonnull CropType type) {
        var item = (Item) CROP_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item crop is null: %s, %s", variant, type));
    }

    @Nonnull
    public static ICropItem getICropItem(@Nonnull CropItemVariant variant, @Nonnull CropType type) {
        var item = CROP_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item crop is null: %s, %s", variant, type));
    }
}
