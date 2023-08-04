package net.dries007.tfc.compat.gregtech.stonetypes;

import gregtech.api.unification.ore.StoneType;
import gregtech.integration.jei.basic.OreByProduct;
import net.dries007.tfc.api.registries.TFCStorage;
import net.minecraft.block.SoundType;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static net.dries007.tfc.api.types.rock.Rock.*;
import static net.dries007.tfc.api.types.rock.RockType.ORDINARY;
import static net.dries007.tfc.api.types.rock.RockVariant.RAW;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix.*;

public class StoneTypeHandler {
    public static void init() {

        OreByProduct.addOreByProductPrefix(oreChunk);

        new StoneType(
                16, "tfc_andesite", SoundType.STONE, oreAndesite, Andesite,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, ANDESITE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, ANDESITE),
                false
        );

        new StoneType(
                17, "tfc_basalt", SoundType.STONE, oreBasalt, Basalt,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, BASALT).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, BASALT),
                false
        );

        new StoneType(
                18, "tfc_chalk", SoundType.STONE, oreChalk, Chalk,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, CHALK).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, CHALK),
                false
        );

        new StoneType(
                19, "tfc_chert", SoundType.STONE, oreChert, Chert,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, CHERT).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, CHERT),
                false
        );

        new StoneType(
                20, "tfc_claystone", SoundType.STONE, oreClaystone, Claystone,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, CLAYSTONE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, CLAYSTONE),
                false
        );

        new StoneType(
                21, "tfc_conglomerate", SoundType.STONE, oreConglomerate, Conglomerate,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, CONGLOMERATE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, CONGLOMERATE),
                false
        );

        new StoneType(
                22, "tfc_dacite", SoundType.STONE, oreDacite, Dacite,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, DACITE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, DACITE),
                false
        );

        new StoneType(
                23, "tfc_diorite", SoundType.STONE, oreDiorite, Diorite,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, DIORITE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, DIORITE),
                false
        );

        new StoneType(
                24, "tfc_dolomite", SoundType.STONE, oreDolomite, Dolomite,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, DOLOMITE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, DOLOMITE),
                false
        );

        new StoneType(
                25, "tfc_gabbro", SoundType.STONE, oreGabbro, Gabbro,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, GABBRO).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, GABBRO),
                false
        );

        new StoneType(
                26, "tfc_gneiss", SoundType.STONE, oreGneiss, Gneiss,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, GNEISS).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, GNEISS),
                false
        );

        new StoneType(
                27, "tfc_granite", SoundType.STONE, oreGranite, Granite,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, GRANITE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, GRANITE),
                false
        );

        new StoneType(
                28, "tfc_limestone", SoundType.STONE, oreLimestone, Limestone,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, LIMESTONE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, LIMESTONE),
                false
        );

        new StoneType(
                29, "tfg_marble", SoundType.STONE, oreMarble, Marble,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, MARBLE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, MARBLE),
                false
        );

        new StoneType(
                30, "tfc_quartzite", SoundType.STONE, oreQuartzite, Quartzite,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, QUARTZITE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, QUARTZITE),
                false
        );

        new StoneType(
                31, "tfc_rhyolite", SoundType.STONE, oreRhyolite, Rhyolite,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, RHYOLITE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, RHYOLITE),
                false
        );

        new StoneType(
                32, "tfc_schist", SoundType.STONE, oreSchist, Schist,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, SCHIST).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, SCHIST),
                false
        );

        new StoneType(
                33, "tfc_shale", SoundType.STONE, oreShale, Shale,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, SHALE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, SHALE),
                false
        );

        new StoneType(
                34, "tfc_slate", SoundType.STONE, oreSlate, Slate,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, SLATE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, SLATE),
                false
        );

        new StoneType(
                35, "tfc_phyllite", SoundType.STONE, orePhyllite, Phyllite,
                () -> TFCStorage.getRockBlock(ORDINARY, RAW, PHYLLITE).getDefaultState(),
                state -> state.getBlock() == TFCStorage.getRockBlock(ORDINARY, RAW, PHYLLITE),
                false
        );
    }
}
