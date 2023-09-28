package net.dries007.tfc.module.food;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.food.api.type.FoodType;
import net.dries007.tfc.module.food.api.variant.Item.FoodItemVariant;
import net.dries007.tfc.module.food.api.variant.Item.IFoodItem;
import net.dries007.tfc.module.food.api.variant.block.FoodBlockVariant;
import net.dries007.tfc.module.food.api.variant.block.IFoodBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Map;

public class StorageFood {

    public static final Map<Pair<FoodBlockVariant, FoodType>, IFoodBlock> FOOD_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<Pair<FoodItemVariant, FoodType>, IFoodItem> FOOD_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    @Nonnull
    public static Block getFoodBlock(@Nonnull FoodBlockVariant variant, @Nonnull FoodType type) {
        var block = (Block) FOOD_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block wood is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IFoodBlock getIFoodBlock(@Nonnull FoodBlockVariant variant, @Nonnull FoodType type) {
        var block = FOOD_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block wood is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Item getFoodItem(@Nonnull FoodItemVariant variant, @Nonnull FoodType type) {
        var item = (Item) FOOD_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s, %s", variant, type));
    }

    @Nonnull
    public static IFoodItem getIFoodItem(@Nonnull FoodItemVariant variant, @Nonnull FoodType type) {
        var item = FOOD_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s, %s", variant, type));
    }
}
