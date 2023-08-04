package net.dries007.tfc.api.registries;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.types2.GroundcoverType;
import net.dries007.tfc.api.types2.plant.Plant;
import net.dries007.tfc.api.types2.plant.PlantVariant;
import net.dries007.tfc.api.types2.plant.util.IPlantBlock;
import net.dries007.tfc.api.types2.rock.Rock;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.rock.util.IRockBlock;
import net.dries007.tfc.api.types2.soil.Soil;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.types2.soil.util.ISoilBlock;
import net.dries007.tfc.api.types2.wood.Wood;
import net.dries007.tfc.api.types2.wood.WoodVariant;
import net.dries007.tfc.api.types2.wood.util.IWoodBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.blocks.BlockAlabaster;
import net.dries007.tfc.objects.blocks.BlockGroundcover;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.objects.items.rock.ItemBrickTFC;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * In this class we can store any blocks, items, and other useful shit.
 */
public final class TFCStorage {

	public static final Map<Triple<RockType, RockVariant, Rock>, IRockBlock> ROCK_BLOCKS = new LinkedHashMap<>();
	public static final Map<Pair<SoilVariant, Soil>, ISoilBlock> SOIL_BLOCKS = new LinkedHashMap<>();
	public static final Map<Pair<PlantVariant, Plant>, IPlantBlock> PLANT_BLOCKS = new LinkedHashMap<>();
	public static final Map<Pair<WoodVariant, Wood>, IWoodBlock> WOOD_BLOCKS = new LinkedHashMap<>();
	public static final Map<Pair<String, RockVariant>, BlockAlabaster> ALABASTER_BLOCK = new LinkedHashMap<>();

	public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new HashMap<>();
	public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new HashMap<>();
	public static final Map<Rock, ItemRock> ITEMROCK_MAP = new HashMap<>();
	public static final Map<Rock, ItemBrickTFC> ITEMBRICK_MAP = new HashMap<>();
	public static final Map<GroundcoverType, BlockGroundcover> GROUNDCOVER_BLOCK = new HashMap<>();

	// Блоки, имеющие предмет
	public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();

	// Блоки, не имеющие предмета
	public static final List<Block> BLOCKS = new ArrayList<>();

	// Предметы
	public static final List<Item> ITEM = new ArrayList<>();

	@Nonnull
	public static Block getRockBlock(@Nonnull RockType rockType, @Nonnull RockVariant blockVariant, @Nonnull Rock stoneType) {
		var block = (Block) ROCK_BLOCKS.get(new Triple<>(rockType, blockVariant, stoneType));
		if (block != null) return block;
		throw new RuntimeException(String.format("Block is null: %s, %s, %s", rockType, blockVariant, stoneType));
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
		throw new RuntimeException(String.format("Item is null: %s", groundcoverType));
	}

	public static void addRockItem(@Nonnull Rock rock, @Nonnull ItemRock itemRock) {
		if (ITEMROCK_MAP.put(rock, itemRock) != null)
			throw new RuntimeException(String.format("Duplicate registry detected: %s", rock));
	}

	@Nonnull
	public static ItemRock getRockItem(@Nonnull Rock rock) {
		var item = (ItemRock) ITEMROCK_MAP.get(rock);
		if (item != null) return item;
		throw new RuntimeException(String.format("Item is null: %s", rock));
	}

	public static void addBrickItem(@Nonnull Rock rock, @Nonnull ItemBrickTFC itemBrick) {
		if (ITEMBRICK_MAP.put(rock, itemBrick) != null)
			throw new RuntimeException(String.format("Duplicate registry detected: %s", rock));
	}

	@Nonnull
	public static ItemBrickTFC getBrickItem(@Nonnull Rock rock) {
		var item = (ItemBrickTFC) ITEMBRICK_MAP.get(rock);
		if (item != null) return item;
		throw new RuntimeException(String.format("Item is null: %s", rock));
	}
}
