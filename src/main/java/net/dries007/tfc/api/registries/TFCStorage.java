package net.dries007.tfc.api.registries;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.rock.util.IRockTypeBlock;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.types2.soil.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.ceramics.ItemUnfiredMold;
import net.minecraft.block.Block;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * In this class we can store any blocks, items, and other useful shit.
 */
public final class TFCStorage {

	public static final Map<Triple<RockBlockType, RockVariant, RockType>, IRockTypeBlock> ROCK_BLOCKS = new HashMap<>();
	public static final Map<Pair<SoilVariant, SoilType>, ISoilTypeBlock> SOIL_BLOCKS = new HashMap<>();

	public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new HashMap<>();
	public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new HashMap<>();

	// Add more, barrels, planks, fences and etc
	public static void addRockBlock(@Nonnull RockBlockType rockBlockType, @Nonnull RockVariant blockVariant, @Nonnull RockType rockType, @Nonnull IRockTypeBlock rockTypeBlock) {
		if (ROCK_BLOCKS.put(new Triple<>(rockBlockType, blockVariant, rockType), rockTypeBlock) != null)
			throw new RuntimeException(String.format("Duplicate registry detected: %s, %s, %s", rockBlockType, blockVariant, rockType));
	}

	@Nonnull
	public static Block getRockBlock(@Nonnull RockBlockType rockBlockType, @Nonnull RockVariant blockVariant, @Nonnull RockType stoneType) {
		var block = (Block) ROCK_BLOCKS.get(new Triple<>(rockBlockType, blockVariant, stoneType));
		if (block != null) return block;
		throw new RuntimeException(String.format("Block is null: %s, %s, %s", rockBlockType, blockVariant, stoneType));
	}

	public static void addSoilBlock(@Nonnull SoilVariant soilVariant, @Nonnull SoilType soilType, @Nonnull ISoilTypeBlock soilTypeBlock) {
		if (SOIL_BLOCKS.put(new Pair<>(soilVariant, soilType), soilTypeBlock) != null)
			throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", soilVariant, soilType));
	}

	@Nonnull
	public static Block getSoilBlock(@Nonnull SoilVariant soilVariant, @Nonnull SoilType soilType) {
		var block = (Block) SOIL_BLOCKS.get(new Pair<>(soilVariant, soilType));
		if (block != null) return block;
		throw new RuntimeException(String.format("Block is null: %s, %s", soilVariant, soilType));
	}
}
