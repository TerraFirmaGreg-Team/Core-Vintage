package net.dries007.tfc.module.rock.api.type;

import net.dries007.tfc.module.rock.api.category.RockCategories;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix.*;
import static net.dries007.tfc.module.rock.api.type.RockTypes.*;

public class RockTypeHandler {

    public static void init() {
        // Igneous Intrusive
        GRANITE = new RockType("granite", RockCategories.IGNEOUS_INTRUSIVE, oreGranite, Granite);
        DIORITE = new RockType("diorite", RockCategories.IGNEOUS_INTRUSIVE, oreDiorite, Diorite);
        GABBRO = new RockType("gabbro", RockCategories.IGNEOUS_INTRUSIVE, oreGabbro, Gabbro);

        // Sedimentary
        SHALE = new RockType("shale", RockCategories.SEDIMENTARY, oreShale, Shale);
        CLAYSTONE = new RockType("claystone", RockCategories.SEDIMENTARY, oreClaystone, Claystone);
        LIMESTONE = new RockType("limestone", RockCategories.SEDIMENTARY, oreLimestone, Limestone);
        CONGLOMERATE = new RockType("conglomerate", RockCategories.SEDIMENTARY, oreConglomerate, Conglomerate);
        DOLOMITE = new RockType("dolomite", RockCategories.SEDIMENTARY, oreDolomite, Dolomite, true);
        CHERT = new RockType("chert", RockCategories.SEDIMENTARY, oreChert, Chert);
        CHALK = new RockType("chalk", RockCategories.SEDIMENTARY, oreChalk, Chalk, true);

        // Igneous Extrusive
        RHYOLITE = new RockType("rhyolite", RockCategories.IGNEOUS_EXTRUSIVE, oreRhyolite, Rhyolite);
        BASALT = new RockType("basalt", RockCategories.IGNEOUS_EXTRUSIVE, oreBasalt, Basalt);
        ANDESITE = new RockType("andesite", RockCategories.IGNEOUS_EXTRUSIVE, oreAndesite, Andesite);
        DACITE = new RockType("dacite", RockCategories.IGNEOUS_EXTRUSIVE, oreDacite, Dacite);

        // Metamorphic
        QUARTZITE = new RockType("quartzite", RockCategories.METAMORPHIC, oreQuartzite, Quartzite, true);
        SLATE = new RockType("slate", RockCategories.METAMORPHIC, oreSlate, Slate);
        PHYLLITE = new RockType("phyllite", RockCategories.METAMORPHIC, orePhyllite, Phyllite);
        SCHIST = new RockType("schist", RockCategories.METAMORPHIC, oreSchist, Schist);
        GNEISS = new RockType("gneiss", RockCategories.METAMORPHIC, oreGneiss, Gneiss);
        MARBLE = new RockType("marble", RockCategories.METAMORPHIC, oreMarble, Marble, true);
    }
}
