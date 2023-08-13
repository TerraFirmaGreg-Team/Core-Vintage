package net.dries007.tfc.api.types.rock.type;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static net.dries007.tfc.api.types.rock.category.RockCategories.*;
import static net.dries007.tfc.api.types.rock.type.RockTypes.*;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix.*;

public class RockTypeHandler {

    public static void init() {
        // Igneous Intrusive
        GRANITE = new RockType("granite", IGNEOUS_INTRUSIVE, oreGranite, Granite);
        DIORITE = new RockType("diorite", IGNEOUS_INTRUSIVE, oreDiorite, Diorite);
        GABBRO = new RockType("gabbro", IGNEOUS_INTRUSIVE, oreGabbro, Gabbro);

        // Sedimentary
        SHALE = new RockType("shale", SEDIMENTARY, oreShale, Shale);
        CLAYSTONE = new RockType("claystone", SEDIMENTARY, oreClaystone, Claystone);
        LIMESTONE = new RockType("limestone", SEDIMENTARY, oreLimestone, Limestone);
        CONGLOMERATE = new RockType("conglomerate", SEDIMENTARY, oreConglomerate, Conglomerate);
        DOLOMITE = new RockType("dolomite", SEDIMENTARY, oreDolomite, Dolomite, true);
        CHERT = new RockType("chert", SEDIMENTARY, oreChert, Chert);
        CHALK = new RockType("chalk", SEDIMENTARY, oreChalk, Chalk, true);

        // Igneous Extrusive
        RHYOLITE = new RockType("rhyolite", IGNEOUS_EXTRUSIVE, oreRhyolite, Rhyolite);
        BASALT = new RockType("basalt", IGNEOUS_EXTRUSIVE, oreBasalt, Basalt);
        ANDESITE = new RockType("andesite", IGNEOUS_EXTRUSIVE, oreAndesite, Andesite);
        DACITE = new RockType("dacite", IGNEOUS_EXTRUSIVE, oreDacite, Dacite);

        // Metamorphic
        QUARTZITE = new RockType("quartzite", METAMORPHIC, oreQuartzite, Quartzite, true);
        SLATE = new RockType("slate", METAMORPHIC, oreSlate, Slate);
        PHYLLITE = new RockType("phyllite", METAMORPHIC, orePhyllite, Phyllite);
        SCHIST = new RockType("schist", METAMORPHIC, oreSchist, Schist);
        GNEISS = new RockType("gneiss", METAMORPHIC, oreGneiss, Gneiss);
        MARBLE = new RockType("marble", METAMORPHIC, oreMarble, Marble, true);
    }
}
