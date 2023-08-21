package net.dries007.tfc.api.registries;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.types.GroundcoverType;
import net.dries007.tfc.api.types.bush.IBushBlock;
import net.dries007.tfc.api.types.bush.type.BushType;
import net.dries007.tfc.api.types.crop.ICropBlock;
import net.dries007.tfc.api.types.crop.type.CropType;
import net.dries007.tfc.api.types.crop.variant.CropBlockVariant;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.metal.IMetalBlock;
import net.dries007.tfc.api.types.metal.variant.MetalBlockVariant;
import net.dries007.tfc.api.types.plant.IPlantBlock;
import net.dries007.tfc.api.types.plant.type.PlantType;
import net.dries007.tfc.api.types.plant.variant.PlantBlockVariant;
import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.common.objects.blocks.BlockGroundcover;
import net.dries007.tfc.common.objects.blocks.rock.BlockAlabaster;
import net.dries007.tfc.common.objects.items.ItemCropSeeds;
import net.dries007.tfc.common.objects.items.ceramics.ItemMold;
import net.dries007.tfc.common.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.common.objects.items.rock.ItemRock;
import net.dries007.tfc.common.objects.items.rock.ItemRockBrick;
import net.dries007.tfc.common.objects.items.wood.ItemWoodBoat;
import net.dries007.tfc.common.objects.items.wood.ItemWoodLumber;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidBase;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * В этом классе мы можем хранить любые блоки, предметы и прочую полезную хрень.
 */
public final class TFCStorage {

    public static final Map<Pair<RockBlockVariant, RockType>, IRockBlock> ROCK_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<SoilBlockVariant, SoilType>, ISoilBlock> SOIL_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<WoodBlockVariant, WoodType>, IWoodBlock> WOOD_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<PlantBlockVariant, PlantType>, IPlantBlock> PLANT_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<CropBlockVariant, CropType>, ICropBlock> CROP_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<MetalBlockVariant, Material>, IMetalBlock> METAL_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<String, RockBlockVariant>, BlockAlabaster> ALABASTER_BLOCKS = new LinkedHashMap<>();
    public static final Map<BushType, IBushBlock> BUSH_BLOCKS = new LinkedHashMap<>();
    public static final Map<GroundcoverType, BlockGroundcover> GROUNDCOVER_BLOCKS = new LinkedHashMap<>();

    public static final Map<RockType, ItemRock> ROCK_ITEMS = new ConcurrentHashMap<>();
    public static final Map<RockType, ItemRockBrick> BRICK_ITEMS = new ConcurrentHashMap<>();
    public static final Map<CropType, ItemCropSeeds> SEED_ITEMS = new ConcurrentHashMap<>();
    public static final Map<FoodType, ItemFoodTFC> FOOD_ITEMS = new LinkedHashMap<>();
    public static final Map<WoodType, ItemWoodLumber> LUMBER_ITEMS = new LinkedHashMap<>();
    public static final Map<WoodType, ItemWoodBoat> BOAT_ITEMS = new LinkedHashMap<>();
    public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new ConcurrentHashMap<>();
    public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new ConcurrentHashMap<>();

    // Блоки, имеющие предмет
    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();

    // Блоки, не имеющие предмета
    public static final List<Block> BLOCKS = new ArrayList<>();

    // Предметы
    public static final List<Item> ITEM = new ArrayList<>();

    // Жидкости
    public static final List<BlockFluidBase> FLUID = new ArrayList<>();

    @Nonnull
    public static Block getRockBlock(@Nonnull RockBlockVariant variant, @Nonnull RockType type) {
        var block = (Block) ROCK_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Block getSoilBlock(@Nonnull SoilBlockVariant variant, @Nonnull SoilType type) {
        var block = (Block) SOIL_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Block getWoodBlock(@Nonnull WoodBlockVariant variant, @Nonnull WoodType type) {
        var block = (Block) WOOD_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Block getPlantBlock(@Nonnull PlantBlockVariant variant, @Nonnull PlantType type) {
        var block = (Block) PLANT_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Block getCropBlock(@Nonnull CropBlockVariant variant, @Nonnull CropType type) {
        var block = (Block) CROP_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, type));
    }

    @Nonnull
    public static Block getAlabasterBlock(@Nonnull String string, @Nonnull RockBlockVariant variant) {
        var block = (Block) ALABASTER_BLOCKS.get(new Pair<>(string, variant));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", string, variant));
    }

    @Nonnull
    public static Block getBushBlock(@Nonnull BushType type) {
        var block = (Block) BUSH_BLOCKS.get(type);
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s", type));
    }

    @Nonnull
    public static Block getGroundcoverBlock(@Nonnull GroundcoverType type) {
        var block = (Block) GROUNDCOVER_BLOCKS.get(type);
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s", type));
    }

    @Nonnull
    public static Block getMetalBlock(@Nonnull MetalBlockVariant variant, @Nonnull Material material) {
        var block = (Block) METAL_BLOCKS.get(new Pair<>(variant, material));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", variant, material));
    }

    @Nonnull
    public static Item getRockItem(@Nonnull RockType type) {
        var item = (Item) ROCK_ITEMS.get(type);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getBrickItem(@Nonnull RockType type) {
        var item = (Item) BRICK_ITEMS.get(type);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getSeedItem(@Nonnull CropType type) {
        var item = (Item) SEED_ITEMS.get(type);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getFoodItem(@Nonnull FoodType type) {
        var item = (Item) FOOD_ITEMS.get(type);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getLumberItem(@Nonnull WoodType type) {
        var item = (Item) LUMBER_ITEMS.get(type);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getBoatItem(@Nonnull WoodType type) {
        var item = (Item) BOAT_ITEMS.get(type);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }
}
