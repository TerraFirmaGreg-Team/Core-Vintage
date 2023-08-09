package net.dries007.tfc.api.types.rock.type;

import static net.dries007.tfc.api.types.rock.category.RockCategories.*;
import static net.dries007.tfc.api.types.rock.type.RockTypes.*;

public class RockTypeHandler {

    public static void init() {
        // Igneous Intrusive
        GRANITE = new RockType("granite", IGNEOUS_INTRUSIVE);
        DIORITE = new RockType("diorite", IGNEOUS_INTRUSIVE);
        GABBRO = new RockType("gabbro", IGNEOUS_INTRUSIVE);

        // Sedimentary
        SHALE = new RockType("shale", SEDIMENTARY);
        CLAYSTONE = new RockType("claystone", SEDIMENTARY);
        LIMESTONE = new RockType("limestone", SEDIMENTARY);
        CONGLOMERATE = new RockType("conglomerate", SEDIMENTARY);
        DOLOMITE = new RockType("dolomite", SEDIMENTARY, true);
        CHERT = new RockType("chert", SEDIMENTARY);
        CHALK = new RockType("chalk", SEDIMENTARY, true);

        // Igneous Extrusive
        RHYOLITE = new RockType("rhyolite", IGNEOUS_EXTRUSIVE);
        BASALT = new RockType("basalt", IGNEOUS_EXTRUSIVE);
        ANDESITE = new RockType("andesite", IGNEOUS_EXTRUSIVE);
        DACITE = new RockType("dacite", IGNEOUS_EXTRUSIVE);

        // Metamorphic
        QUARTZITE = new RockType("quartzite", METAMORPHIC, true);
        SLATE = new RockType("slate", METAMORPHIC);
        PHYLLITE = new RockType("phyllite", METAMORPHIC);
        SCHIST = new RockType("schist", METAMORPHIC);
        GNEISS = new RockType("gneiss", METAMORPHIC);
        MARBLE = new RockType("marble", METAMORPHIC, true);
    }
}
