package net.dries007.tfc.api.registries;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.types.GroundcoverType;
import net.dries007.tfc.api.types.metal.MetalVariant;
import net.dries007.tfc.api.types.metal.util.IMetalBlock;
import net.dries007.tfc.api.types.plant.Plant;
import net.dries007.tfc.api.types.plant.PlantVariant;
import net.dries007.tfc.api.types.plant.util.IPlantBlock;
import net.dries007.tfc.api.types.rock.Rock;
import net.dries007.tfc.api.types.rock.RockType;
import net.dries007.tfc.api.types.rock.RockVariant;
import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.util.IRockBlock;
import net.dries007.tfc.api.types.soil.Soil;
import net.dries007.tfc.api.types.soil.SoilVariant;
import net.dries007.tfc.api.types.soil.util.ISoilBlock;
import net.dries007.tfc.api.types.wood.Wood;
import net.dries007.tfc.api.types.wood.WoodVariant;
import net.dries007.tfc.api.types.wood.util.IWoodBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.blocks.BlockAlabaster;
import net.dries007.tfc.objects.blocks.BlockGroundcover;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.items.rock.ItemRockBrick;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * В этом классе мы можем хранить любые блоки, предметы и прочую полезную хрень.
 */
public final class TFCStorage {

    public static final Map<Triple<RockType, RockVariant, Rock>, IRockBlock> ROCK_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<SoilVariant, Soil>, ISoilBlock> SOIL_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<PlantVariant, Plant>, IPlantBlock> PLANT_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<WoodVariant, Wood>, IWoodBlock> WOOD_BLOCKS = new LinkedHashMap<>();
    public static final Map<Pair<String, RockVariant>, BlockAlabaster> ALABASTER_BLOCK = new LinkedHashMap<>();
    public static final Map<Pair<MetalVariant, Material>, IMetalBlock> METAL_BLOCKS = new LinkedHashMap<>();
    public static final Map<GroundcoverType, BlockGroundcover> GROUNDCOVER_BLOCK = new HashMap<>();

    public static final Map<Rock, ItemRock> ROCK_ITEM = new HashMap<>();
    public static final Map<Rock, ItemRockBrick> BRICK_ITEM = new HashMap<>();
    public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new HashMap<>();
    public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new HashMap<>();

    // Блоки, имеющие предмет
    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();

    // Блоки, не имеющие предмета
    public static final List<Block> BLOCKS = new ArrayList<>();

    // Предметы
    public static final List<Item> ITEM = new ArrayList<>();

    // Жидкости
    public static final List<BlockFluidBase> FLUID = new ArrayList<>();

    @Nonnull
    public static Block getRockBlock(@Nonnull RockBlockType rockBlockType, @Nullable RockBlockVariant blockVariant, @Nonnull RockType rockType) {
        var block = (Block) ROCK_BLOCKS.get(new Triple<>(rockBlockType, blockVariant, rockType));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s, %s", rockBlockType, blockVariant, rockType));
    }

    @Nonnull
    public static Block getRockBlock(@Nonnull RockBlockType rockBlockType, @Nonnull RockType rockType) {
        return getRockBlock(rockBlockType, null, rockType);
    }

    @Nonnull
    public static Block getSoilBlock(@Nonnull SoilVariant soilVariant, @Nonnull Soil soil) {
        var block = (Block) SOIL_BLOCKS.get(new Pair<>(soilVariant, soil));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", soilVariant, soil));
    }

    @Nonnull
    public static Block getPlantBlock(@Nonnull PlantVariant plantVariant, @Nonnull Plant plant) {
        var block = (Block) PLANT_BLOCKS.get(new Pair<>(plantVariant, plant));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", plantVariant, plant));
    }

    @Nonnull
    public static Block getWoodBlock(@Nonnull WoodVariant woodVariant, @Nonnull Wood wood) {
        var block = (Block) WOOD_BLOCKS.get(new Pair<>(woodVariant, wood));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", woodVariant, wood));
    }

    @Nonnull
    public static Block getAlabasterBlock(@Nonnull String string, @Nonnull RockVariant rockVariant) {
        var block = (Block) ALABASTER_BLOCK.get(new Pair<>(string, rockVariant));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", string, rockVariant));
    }

    @Nonnull
    public static Block getGroundcoverBlock(@Nonnull GroundcoverType groundcoverType) {
        var block = (Block) GROUNDCOVER_BLOCK.get(groundcoverType);
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s", groundcoverType));
    }

    @Nonnull
    public static Block getMetalBlock(@Nonnull MetalVariant metalVariant, @Nonnull Material material) {
        var block = (Block) METAL_BLOCKS.get(new Pair<>(metalVariant, material));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", metalVariant, material));
    }

    @Nonnull
    public static Item getRockItem(@Nonnull Rock rock) {
        var item = (Item) ROCK_ITEM.get(rock);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", rock));
    }


    @Nonnull
    public static Item getBrickItem(@Nonnull Rock rock) {
        var item = (Item) BRICK_ITEM.get(rock);
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", rock));
    }
}
