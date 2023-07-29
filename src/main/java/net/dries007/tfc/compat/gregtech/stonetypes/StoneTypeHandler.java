package net.dries007.tfc.compat.gregtech.stonetypes;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
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
import static net.dries007.tfc.objects.blocks.rock.BlockRockVariant.getBlockRockMap;

public class StoneTypeHandler {

    @SuppressWarnings("ConstantConditions")
    public static void init() {

        final var graniteBlock = getBlockRockMap(ORDINARY, RAW, GRANITE);
        final var dioriteBlock = getBlockRockMap(ORDINARY, RAW, DIORITE);
        final var gabbroBlock = getBlockRockMap(ORDINARY, RAW, GABBRO);
        final var shaleBlock = getBlockRockMap(ORDINARY, RAW, SHALE);
        final var claystoneBlock = getBlockRockMap(ORDINARY, RAW, CLAYSTONE);
        final var limestoneBlock = getBlockRockMap(ORDINARY, RAW, LIMESTONE);
        final var conglomerateBlock = getBlockRockMap(ORDINARY, RAW, CONGLOMERATE);
        final var dolomiteBlock = getBlockRockMap(ORDINARY, RAW, DOLOMITE);
        final var chertBlock = getBlockRockMap(ORDINARY, RAW, CHERT);
        final var chalkBlock = getBlockRockMap(ORDINARY, RAW, CHALK);
        final var rhyoliteBlock = getBlockRockMap(ORDINARY, RAW, RHYOLITE);
        final var basaltBlock = getBlockRockMap(ORDINARY, RAW, BASALT);
        final var andesiteBlock = getBlockRockMap(ORDINARY, RAW, ANDESITE);
        final var daciteBlock = getBlockRockMap(ORDINARY, RAW, DACITE);
        final var quartziteBlock = getBlockRockMap(ORDINARY, RAW, QUARTZITE);
        final var slateBlock = getBlockRockMap(ORDINARY, RAW, SLATE);
        final var phylliteBlock = getBlockRockMap(ORDINARY, RAW, PHYLLITE);
        final var schistBlock = getBlockRockMap(ORDINARY, RAW, SCHIST);
        final var gneissBlock = getBlockRockMap(ORDINARY, RAW, GNEISS);
        final var marbleBlock = getBlockRockMap(ORDINARY, RAW, MARBLE);

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
