package net.dries007.tfc.compat.gregtech.stonetypes;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.objects.blocks.rock.BlockRock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

import javax.annotation.Nonnull;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix.*;

public class StoneTypeHandler {

    @SuppressWarnings("ConstantConditions")
    public static void init() {

        final var graniteBlock = BlockRock.getBlockRockMapRaw(RockType.GRANITE);
        final var dioriteBlock = BlockRock.getBlockRockMapRaw(RockType.DIORITE);
        final var gabbroBlock = BlockRock.getBlockRockMapRaw(RockType.GABBRO);
        final var shaleBlock = BlockRock.getBlockRockMapRaw(RockType.SHALE);
        final var claystoneBlock = BlockRock.getBlockRockMapRaw(RockType.CLAYSTONE);
        final var limestoneBlock = BlockRock.getBlockRockMapRaw(RockType.LIMESTONE);
        final var conglomerateBlock = BlockRock.getBlockRockMapRaw(RockType.CONGLOMERATE);
        final var dolomiteBlock = BlockRock.getBlockRockMapRaw(RockType.DOLOMITE);
        final var chertBlock = BlockRock.getBlockRockMapRaw(RockType.CHERT);
        final var chalkBlock = BlockRock.getBlockRockMapRaw(RockType.CHALK);
        final var rhyoliteBlock = BlockRock.getBlockRockMapRaw(RockType.RHYOLITE);
        final var basaltBlock = BlockRock.getBlockRockMapRaw(RockType.BASALT);
        final var andesiteBlock = BlockRock.getBlockRockMapRaw(RockType.ANDESITE);
        final var daciteBlock = BlockRock.getBlockRockMapRaw(RockType.DACITE);
        final var quartziteBlock = BlockRock.getBlockRockMapRaw(RockType.QUARTZITE);
        final var slateBlock = BlockRock.getBlockRockMapRaw(RockType.SLATE);
        final var phylliteBlock = BlockRock.getBlockRockMapRaw(RockType.PHYLLITE);
        final var schistBlock = BlockRock.getBlockRockMapRaw(RockType.SCHIST);
        final var gneissBlock = BlockRock.getBlockRockMapRaw(RockType.GNEISS);
        final var marbleBlock = BlockRock.getBlockRockMapRaw(RockType.MARBLE);

        createStoneType(16, "tfc_andesite", oreAndesite, Andesite, andesiteBlock);
        createStoneType(17, "tfc_basalt", oreBasalt, Basalt, basaltBlock);
        createStoneType(18, "tfc_chalk", oreChalk, Chalk, chalkBlock);
        createStoneType(19, "tfc_chert", oreChert, Chert, chertBlock);
        createStoneType(20, "tfc_claystone", oreClaystone, Claystone, claystoneBlock);
        createStoneType(21, "tfc_conglomerate", oreConglomerate, Conglomerate, conglomerateBlock);
        createStoneType(22, "tfc_dacite", oreDacite, Dacite, daciteBlock);
        createStoneType(23, "tfc_diorite", oreDiorite, Diorite, dioriteBlock);
        createStoneType(24, "tfc_dolomite",oreDolomite, Dolomite, dolomiteBlock);
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
