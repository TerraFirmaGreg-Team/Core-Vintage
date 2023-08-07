package net.dries007.tfc.api.types.rock.type;

import net.dries007.tfc.api.types.rock.category.RockCategories;

public class RockTypeHandler {

    public static void init() {
        // Igneous Intrusive
        RockTypes.Granite = new RockType("granite", RockCategories.IgneousIntrusive);
        RockTypes.Diorite = new RockType("diorite", RockCategories.IgneousIntrusive);
        RockTypes.Gabbro = new RockType("gabbro", RockCategories.IgneousIntrusive);

        // Sedimentary
        RockTypes.Shale = new RockType("shale", RockCategories.Sedimentary);
        RockTypes.Claystone = new RockType("claystone", RockCategories.Sedimentary);
        RockTypes.Limestone = new RockType("limestone", RockCategories.Sedimentary);
        RockTypes.Conglomerate = new RockType("conglomerate", RockCategories.Sedimentary);
        RockTypes.Dolomite = new RockType("dolomite", RockCategories.Sedimentary, true);
        RockTypes.Chert = new RockType("chert", RockCategories.Sedimentary);
        RockTypes.Chalk = new RockType("chalk", RockCategories.Sedimentary, true);

        // Igneous Extrusive
        RockTypes.Rhyolite = new RockType("rhyolite", RockCategories.IgneousExtrusive);
        RockTypes.Basalt = new RockType("basalt", RockCategories.IgneousExtrusive);
        RockTypes.Andesite = new RockType("andesite", RockCategories.IgneousExtrusive);
        RockTypes.Dacite = new RockType("dacite", RockCategories.IgneousExtrusive);

        // Metamorphic
        RockTypes.Quartzite = new RockType("quartzite", RockCategories.Metamorphic, true);
        RockTypes.Slate = new RockType("slate", RockCategories.Metamorphic);
        RockTypes.Phyllite = new RockType("phyllite", RockCategories.Metamorphic);
        RockTypes.Schist = new RockType("schist", RockCategories.Metamorphic);
        RockTypes.Gneiss = new RockType("gneiss", RockCategories.Metamorphic);
        RockTypes.Marble = new RockType("marble", RockCategories.Metamorphic, true);
    }
}
