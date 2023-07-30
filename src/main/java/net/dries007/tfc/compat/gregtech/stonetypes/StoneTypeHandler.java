package net.dries007.tfc.compat.gregtech.stonetypes;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import net.dries007.tfc.api.registries.TFCStorage;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

import javax.annotation.Nonnull;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static net.dries007.tfc.api.types2.rock.RockBlockType.ORDINARY;
import static net.dries007.tfc.api.types2.rock.RockType.*;
import static net.dries007.tfc.api.types2.rock.RockVariant.RAW;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix.*;

public class StoneTypeHandler {

    @SuppressWarnings("ConstantConditions")
    public static void init() {

        final var graniteBlock = TFCStorage.getRockBlock(ORDINARY, RAW, GRANITE);
        final var dioriteBlock = TFCStorage.getRockBlock(ORDINARY, RAW, DIORITE);
        final var gabbroBlock = TFCStorage.getRockBlock(ORDINARY, RAW, GABBRO);
        final var shaleBlock = TFCStorage.getRockBlock(ORDINARY, RAW, SHALE);
        final var claystoneBlock = TFCStorage.getRockBlock(ORDINARY, RAW, CLAYSTONE);
        final var limestoneBlock = TFCStorage.getRockBlock(ORDINARY, RAW, LIMESTONE);
        final var conglomerateBlock = TFCStorage.getRockBlock(ORDINARY, RAW, CONGLOMERATE);
        final var dolomiteBlock = TFCStorage.getRockBlock(ORDINARY, RAW, DOLOMITE);
        final var chertBlock = TFCStorage.getRockBlock(ORDINARY, RAW, CHERT);
        final var chalkBlock = TFCStorage.getRockBlock(ORDINARY, RAW, CHALK);
        final var rhyoliteBlock = TFCStorage.getRockBlock(ORDINARY, RAW, RHYOLITE);
        final var basaltBlock = TFCStorage.getRockBlock(ORDINARY, RAW, BASALT);
        final var andesiteBlock = TFCStorage.getRockBlock(ORDINARY, RAW, ANDESITE);
        final var daciteBlock = TFCStorage.getRockBlock(ORDINARY, RAW, DACITE);
        final var quartziteBlock = TFCStorage.getRockBlock(ORDINARY, RAW, QUARTZITE);
        final var slateBlock = TFCStorage.getRockBlock(ORDINARY, RAW, SLATE);
        final var phylliteBlock = TFCStorage.getRockBlock(ORDINARY, RAW, PHYLLITE);
        final var schistBlock = TFCStorage.getRockBlock(ORDINARY, RAW, SCHIST);
        final var gneissBlock = TFCStorage.getRockBlock(ORDINARY, RAW, GNEISS);
        final var marbleBlock = TFCStorage.getRockBlock(ORDINARY, RAW, MARBLE);

        createStoneType(16, "tfc_andesite", oreAndesite, Andesite, andesiteBlock);
        createStoneType(17, "tfc_basalt", oreBasalt, Basalt, basaltBlock);
        createStoneType(18, "tfc_chalk", oreChalk, Chalk, chalkBlock);
        createStoneType(19, "tfc_chert", oreChert, Chert, chertBlock);
        createStoneType(20, "tfc_claystone", oreClaystone, Claystone, claystoneBlock);
        createStoneType(21, "tfc_conglomerate", oreConglomerate, Conglomerate, conglomerateBlock);
        createStoneType(22, "tfc_dacite", oreDacite, Dacite, daciteBlock);
        createStoneType(23, "tfc_diorite", oreDiorite, Diorite, dioriteBlock);
        createStoneType(24, "tfc_dolomite", oreDolomite, Dolomite, dolomiteBlock);
        createStoneType(25, "tfc_gabbro", oreGabbro, Gabbro, gabbroBlock);
        createStoneType(26, "tfc_gneiss", oreGneiss, Gneiss, gneissBlock);
        createStoneType(27, "tfc_granite", oreGranite, Granite, graniteBlock);
        createStoneType(28, "tfc_limestone", oreLimestone, Limestone, limestoneBlock);
        createStoneType(29, "tfg_marble", oreMarble, Marble, marbleBlock);
        createStoneType(30, "tfc_quartzite", oreQuartzite, Quartzite, quartziteBlock);
        createStoneType(31, "tfc_rhyolite", oreRhyolite, Rhyolite, rhyoliteBlock);
        createStoneType(32, "tfc_schist", oreSchist, Schist, schistBlock);
        createStoneType(33, "tfc_shale", oreShale, Shale, shaleBlock);
        createStoneType(34, "tfc_slate", oreSlate, Slate, slateBlock);
        createStoneType(35, "tfc_phyllite", orePhyllite, Phyllite, phylliteBlock);
    }

    private static void createStoneType(int id, @Nonnull String name, @Nonnull OrePrefix orePrefix, @Nonnull Material material, @Nonnull Block predicateBlock) {
        new StoneType(
                id,
                name,
                SoundType.STONE,
                orePrefix,
                material,
                predicateBlock::getDefaultState,
                state -> state.getBlock() == predicateBlock,
                false
        );
    }
}
