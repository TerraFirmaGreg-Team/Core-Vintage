package net.dries007.tfc.module.core.submodule.soil.common;

import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.core.submodule.soil.api.variant.block.ISoilBlock;
import net.dries007.tfc.module.core.submodule.soil.api.variant.item.ISoilItem;
import net.dries007.tfc.module.core.submodule.soil.api.type.SoilType;
import net.dries007.tfc.module.core.submodule.soil.api.variant.block.SoilBlockVariant;
import net.dries007.tfc.module.core.submodule.soil.api.variant.item.SoilItemVariant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

public class SoilStorage {
    public static final Map<Pair<SoilBlockVariant, SoilType>, ISoilBlock> SOIL_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<SoilItemVariant, SoilType>, ISoilItem> SOIL_ITEMS = new LinkedHashMap<>();

    @Nonnull
    public static Block getSoilBlock(@Nonnull SoilBlockVariant variant, @Nonnull SoilType type) {
        var block = (Block) SOIL_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Item getSoilItem(@Nonnull SoilItemVariant variant, @Nonnull SoilType type) {
        var item = (Item) SOIL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s, %s", variant, type));
    }
}
