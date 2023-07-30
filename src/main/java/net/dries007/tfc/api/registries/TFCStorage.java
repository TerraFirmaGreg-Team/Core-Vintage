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

import java.util.HashMap;
import java.util.Map;

/**
 * In this class we can store any blocks, items, and other useful shit.
 * */
public final class TFCStorage {

    public static final Map<Triple<RockBlockType, RockVariant, RockType>, IRockTypeBlock> ROCK_BLOCKS = new HashMap<>();
    public static final Map<Pair<SoilVariant, SoilType>, ISoilTypeBlock> SOIL_BLOCKS = new HashMap<>();

    public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new HashMap<>();
    public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new HashMap<>();

    // Add more, barrels, planks, fences and etc

    public static void addRockBlock(RockBlockType rockBlockType, RockVariant blockVariant, RockType rockType, IRockTypeBlock rockTypeBlock) {
        if (ROCK_BLOCKS.put(new Triple<>(rockBlockType, blockVariant, rockType), rockTypeBlock) != null) {
            throw new RuntimeException(
                    String.format("Duplicate registry detected: %s, %s, %s", rockBlockType, blockVariant, rockType)
            );
        }
    }

    public static Block getRockBlock(RockBlockType rockBlockType, RockVariant blockVariant, RockType stoneType) {
        return (Block) ROCK_BLOCKS.get(new Triple<>(rockBlockType, blockVariant, stoneType));
    }

    public static void addSoilBlock(SoilVariant soilVariant, SoilType soilType, ISoilTypeBlock soilTypeBlock) {
        if (SOIL_BLOCKS.put(new Pair<>(soilVariant, soilType), soilTypeBlock) != null) {
            throw new RuntimeException(
                    String.format("Duplicate registry detected: %s, %s", soilVariant, soilType)
            );
        }
    }

    public static Block getSoilBlock(SoilVariant soilVariant, SoilType soilType) {
        return (Block) SOIL_BLOCKS.get(new Pair<>(soilVariant, soilType));
    }
}
