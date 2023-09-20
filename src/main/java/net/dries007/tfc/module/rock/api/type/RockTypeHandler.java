package net.dries007.tfc.module.rock.api.type;

import net.dries007.tfc.module.rock.api.category.RockCategories;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix.*;

public class RockTypeHandler {

    public static void init() {
        // Igneous Intrusive
        RockTypes.GRANITE = new RockType("granite", RockCategories.IGNEOUS_INTRUSIVE, oreGranite, Granite);
        RockTypes.DIORITE = new RockType("diorite", RockCategories.IGNEOUS_INTRUSIVE, oreDiorite, Diorite);
        RockTypes.GABBRO = new RockType("gabbro", RockCategories.IGNEOUS_INTRUSIVE, oreGabbro, Gabbro);

        // Sedimentary
        RockTypes.SHALE = new RockType("shale", RockCategories.SEDIMENTARY, oreShale, Shale);
        RockTypes.CLAYSTONE = new RockType("claystone", RockCategories.SEDIMENTARY, oreClaystone, Claystone);
        RockTypes.LIMESTONE = new RockType("limestone", RockCategories.SEDIMENTARY, oreLimestone, Limestone);
        RockTypes.CONGLOMERATE = new RockType("conglomerate", RockCategories.SEDIMENTARY, oreConglomerate, Conglomerate);
        RockTypes.DOLOMITE = new RockType("dolomite", RockCategories.SEDIMENTARY, oreDolomite, Dolomite, true);
        RockTypes.CHERT = new RockType("chert", RockCategories.SEDIMENTARY, oreChert, Chert);
        RockTypes.CHALK = new RockType("chalk", RockCategories.SEDIMENTARY, oreChalk, Chalk, true);

        // Igneous Extrusive
        RockTypes.RHYOLITE = new RockType("rhyolite", RockCategories.IGNEOUS_EXTRUSIVE, oreRhyolite, Rhyolite);
        RockTypes.BASALT = new RockType("basalt", RockCategories.IGNEOUS_EXTRUSIVE, oreBasalt, Basalt);
        RockTypes.ANDESITE = new RockType("andesite", RockCategories.IGNEOUS_EXTRUSIVE, oreAndesite, Andesite);
        RockTypes.DACITE = new RockType("dacite", RockCategories.IGNEOUS_EXTRUSIVE, oreDacite, Dacite);

        // Metamorphic
        RockTypes.QUARTZITE = new RockType("quartzite", RockCategories.METAMORPHIC, oreQuartzite, Quartzite, true);
        RockTypes.SLATE = new RockType("slate", RockCategories.METAMORPHIC, oreSlate, Slate);
        RockTypes.PHYLLITE = new RockType("phyllite", RockCategories.METAMORPHIC, orePhyllite, Phyllite);
        RockTypes.SCHIST = new RockType("schist", RockCategories.METAMORPHIC, oreSchist, Schist);
        RockTypes.GNEISS = new RockType("gneiss", RockCategories.METAMORPHIC, oreGneiss, Gneiss);
        RockTypes.MARBLE = new RockType("marble", RockCategories.METAMORPHIC, oreMarble, Marble, true);
    }
}
