package net.dries007.tfc.api.registries;

import gregtech.api.unification.ore.OrePrefix;
import java.util.*;
import javax.annotation.Nonnull;
import net.dries007.tfc.api.types2.plant.PlantType;
import net.dries007.tfc.api.types2.plant.PlantVariant;
import net.dries007.tfc.api.types2.plant.util.IPlantBlock;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.rock.util.IRockBlock;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.types2.soil.util.ISoilBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.blocks.BlockAlabaster;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.objects.items.rock.ItemBrickTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * In this class we can store any blocks, items, and other useful shit.
 */
public final class TFCStorage {

    public static final Map<Triple<RockBlockType, RockVariant, RockType>, IRockBlock> ROCK_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<SoilVariant, SoilType>, ISoilBlock> SOIL_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<PlantVariant, PlantType>, IPlantBlock> PLANT_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<String, RockVariant>, BlockAlabaster> ALABASTER_BLOCK = new LinkedHashMap<>();

    public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new HashMap<>();
    public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new HashMap<>();
    public static final Map<RockType, ItemRock> ITEMROCK_MAP = new HashMap<>();
    public static final Map<RockType, ItemBrickTFC> ITEMBRICK_MAP = new HashMap<>();

    public static final List<ItemBlock> NORMAL_ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<Item> ITEM = new ArrayList<>();

    @Nonnull
    public static Block getRockBlock(@Nonnull RockBlockType rockBlockType, @Nonnull RockVariant blockVariant, @Nonnull RockType stoneType) {
        var block = (Block) ROCK_BLOCKS.get(new Triple<>(rockBlockType, blockVariant, stoneType));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s, %s", rockBlockType, blockVariant, stoneType));
    }

    @Nonnull
    public static Block getSoilBlock(@Nonnull SoilVariant soilVariant, @Nonnull SoilType soilType) {
        var block = (Block) SOIL_BLOCKS.get(new Pair<>(soilVariant, soilType));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", soilVariant, soilType));
    }

    @Nonnull
    public static Block getPlantBlock(@Nonnull PlantVariant plantVariant, @Nonnull PlantType plantType) {
        var block = (Block) PLANT_BLOCKS.get(new Pair<>(plantVariant, plantType));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", plantVariant, plantType));
    }

    @Nonnull
    public static Block getAlabasterBlock(@Nonnull String string, @Nonnull RockVariant rockVariant) {
        var block = (Block) ALABASTER_BLOCK.get(new Pair<>(string, rockVariant));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", string, rockVariant));
    }

    public static void addRockItem(@Nonnull RockType rockType, @Nonnull ItemRock itemRock) {
        if (ITEMROCK_MAP.put(rockType, itemRock) != null)
            throw new RuntimeException(String.format("Duplicate registry detected: %s", rockType));
    }

    @Nonnull
    public static ItemRock getRockItem(@Nonnull RockType rockType) {
        var item = (ItemRock) ITEMROCK_MAP.get(rockType);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", rockType));
    }

    public static void addBrickItem(@Nonnull RockType rockType, @Nonnull ItemBrickTFC itemBrick) {
        if (ITEMBRICK_MAP.put(rockType, itemBrick) != null)
            throw new RuntimeException(String.format("Duplicate registry detected: %s", rockType));
    }

    @Nonnull
    public static ItemBrickTFC getBrickItem(@Nonnull RockType rockType) {
        var item = (ItemBrickTFC) ITEMBRICK_MAP.get(rockType);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", rockType));
    }
}
