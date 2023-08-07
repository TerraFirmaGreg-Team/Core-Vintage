package net.dries007.tfc.api.types.rock.type;

import static net.dries007.tfc.api.types.rock.category.RockCategories.*;
import static net.dries007.tfc.api.types.rock.type.Rocks.*;

public class RockHandler {

    public static void init() {
        // Igneous Intrusive
        GRANITE = new Rock("granite", IGNEOUS_INTRUSIVE);
        DIORITE = new Rock("diorite", IGNEOUS_INTRUSIVE);
        GABBRO = new Rock("gabbro", IGNEOUS_INTRUSIVE);

        // Sedimentary
        SHALE = new Rock("shale", SEDIMENTARY);
        CLAYSTONE = new Rock("claystone", SEDIMENTARY);
        LIMESTONE = new Rock("limestone", SEDIMENTARY);
        CONGLOMERATE = new Rock("conglomerate", SEDIMENTARY);
        DOLOMITE = new Rock("dolomite", SEDIMENTARY, true);
        CHERT = new Rock("chert", SEDIMENTARY);
        CHALK = new Rock("chalk", SEDIMENTARY, true);

        // Igneous Extrusive
        RHYOLITE = new Rock("rhyolite", IGNEOUS_EXTRUSIVE);
        BASALT = new Rock("basalt", IGNEOUS_EXTRUSIVE);
        ANDESITE = new Rock("andesite", IGNEOUS_EXTRUSIVE);
        DACITE = new Rock("dacite", IGNEOUS_EXTRUSIVE);

        // Metamorphic
        QUARTZITE = new Rock("quartzite", METAMORPHIC, true);
        SLATE = new Rock("slate", METAMORPHIC);
        PHYLLITE = new Rock("phyllite", METAMORPHIC);
        SCHIST = new Rock("schist", METAMORPHIC);
        GNEISS = new Rock("gneiss", METAMORPHIC);
        MARBLE = new Rock("marble", METAMORPHIC, true);
    }
}
