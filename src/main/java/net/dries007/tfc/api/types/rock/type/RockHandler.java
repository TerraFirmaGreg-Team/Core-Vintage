package net.dries007.tfc.api.types.rock.type;

import net.dries007.tfc.api.types.rock.category.RockCategories;

import static net.dries007.tfc.api.types.rock.type.Rocks.*;

public class RockHandler {

    public static void init() {
        // Igneous Intrusive
        GRANITE = new Rock("granite", RockCategories.IGNEOUS_INTRUSIVE);
        DIORITE = new Rock("diorite", RockCategories.IGNEOUS_INTRUSIVE);
        GABBRO = new Rock("gabbro", RockCategories.IGNEOUS_INTRUSIVE);

        // Sedimentary
        SHALE = new Rock("shale", RockCategories.SEDIMENTARY);
        CLAYSTONE = new Rock("claystone", RockCategories.SEDIMENTARY);
        LIMESTONE = new Rock("limestone", RockCategories.SEDIMENTARY);
        CONGLOMERATE = new Rock("conglomerate", RockCategories.SEDIMENTARY);
        DOLOMITE = new Rock("dolomite", RockCategories.SEDIMENTARY, true);
        CHERT = new Rock("chert", RockCategories.SEDIMENTARY);
        CHALK = new Rock("chalk", RockCategories.SEDIMENTARY, true);

        // Igneous Extrusive
        RHYOLITE = new Rock("rhyolite", RockCategories.IGNEOUS_EXTRUSIVE);
        BASALT = new Rock("basalt", RockCategories.IGNEOUS_EXTRUSIVE);
        ANDESITE = new Rock("andesite", RockCategories.IGNEOUS_EXTRUSIVE);
        DACITE = new Rock("dacite", RockCategories.IGNEOUS_EXTRUSIVE);

        // Metamorphic
        QUARTZITE = new Rock("quartzite", RockCategories.METAMORPHIC, true);
        SLATE = new Rock("slate", RockCategories.METAMORPHIC);
        PHYLLITE = new Rock("phyllite", RockCategories.METAMORPHIC);
        SCHIST = new Rock("schist", RockCategories.METAMORPHIC);
        GNEISS = new Rock("gneiss", RockCategories.METAMORPHIC);
        MARBLE = new Rock("marble", RockCategories.METAMORPHIC, true);
    }
}
