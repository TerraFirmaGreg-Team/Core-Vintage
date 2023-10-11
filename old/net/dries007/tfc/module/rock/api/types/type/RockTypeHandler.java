package net.dries007.tfc.module.rock.api.types.type;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.dries007.tfc.module.rock.api.types.category.RockCategories;

public class RockTypeHandler {

    public static void init() {
        // Igneous Intrusive
        RockTypes.GRANITE = new RockType("granite", RockCategories.IGNEOUS_INTRUSIVE, OrePrefix.oreGranite, Materials.Granite);
        RockTypes.DIORITE = new RockType("diorite", RockCategories.IGNEOUS_INTRUSIVE, OrePrefix.oreDiorite, Materials.Diorite);
        RockTypes.GABBRO = new RockType("gabbro", RockCategories.IGNEOUS_INTRUSIVE, TFGOrePrefix.oreGabbro, TFGMaterials.Gabbro);

        // Sedimentary
        RockTypes.SHALE = new RockType("shale", RockCategories.SEDIMENTARY, TFGOrePrefix.oreShale, TFGMaterials.Shale);
        RockTypes.CLAYSTONE = new RockType("claystone", RockCategories.SEDIMENTARY, TFGOrePrefix.oreClaystone, TFGMaterials.Claystone);
        RockTypes.LIMESTONE = new RockType("limestone", RockCategories.SEDIMENTARY, TFGOrePrefix.oreLimestone, TFGMaterials.Limestone);
        RockTypes.CONGLOMERATE = new RockType("conglomerate", RockCategories.SEDIMENTARY, TFGOrePrefix.oreConglomerate, TFGMaterials.Conglomerate);
        RockTypes.DOLOMITE = new RockType("dolomite", RockCategories.SEDIMENTARY, TFGOrePrefix.oreDolomite, TFGMaterials.Dolomite, true);
        RockTypes.CHERT = new RockType("chert", RockCategories.SEDIMENTARY, TFGOrePrefix.oreChert, TFGMaterials.Chert);
        RockTypes.CHALK = new RockType("chalk", RockCategories.SEDIMENTARY, TFGOrePrefix.oreChalk, TFGMaterials.Chalk, true);

        // Igneous Extrusive
        RockTypes.RHYOLITE = new RockType("rhyolite", RockCategories.IGNEOUS_EXTRUSIVE, TFGOrePrefix.oreRhyolite, TFGMaterials.Rhyolite);
        RockTypes.BASALT = new RockType("basalt", RockCategories.IGNEOUS_EXTRUSIVE, OrePrefix.oreBasalt, Materials.Basalt);
        RockTypes.ANDESITE = new RockType("andesite", RockCategories.IGNEOUS_EXTRUSIVE, OrePrefix.oreAndesite, Materials.Andesite);
        RockTypes.DACITE = new RockType("dacite", RockCategories.IGNEOUS_EXTRUSIVE, TFGOrePrefix.oreDacite, TFGMaterials.Dacite);

        // Metamorphic
        RockTypes.QUARTZITE = new RockType("quartzite", RockCategories.METAMORPHIC, TFGOrePrefix.oreQuartzite, Materials.Quartzite, true);
        RockTypes.SLATE = new RockType("slate", RockCategories.METAMORPHIC, TFGOrePrefix.oreSlate, TFGMaterials.Slate);
        RockTypes.PHYLLITE = new RockType("phyllite", RockCategories.METAMORPHIC, TFGOrePrefix.orePhyllite, TFGMaterials.Phyllite);
        RockTypes.SCHIST = new RockType("schist", RockCategories.METAMORPHIC, TFGOrePrefix.oreSchist, TFGMaterials.Schist);
        RockTypes.GNEISS = new RockType("gneiss", RockCategories.METAMORPHIC, TFGOrePrefix.oreGneiss, TFGMaterials.Gneiss);
        RockTypes.MARBLE = new RockType("marble", RockCategories.METAMORPHIC, OrePrefix.oreMarble, Materials.Marble, true);
    }
}
