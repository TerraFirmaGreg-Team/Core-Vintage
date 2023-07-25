package net.dries007.tfc.compat.gregtech.material;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.unification.material.registry.MaterialRegistry;
import gregtech.core.unification.material.internal.MaterialRegistryManager;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.RedSteel;
import static gregtech.api.util.GTUtility.gregtechId;

public class TFGMaterialHandler {

    public static void init() {
        TFGMaterials.Catlinite = new Material.Builder(32101, gregtechId("catlinite"))
                .dust()
                .color(0xB46C62)
                .build();

        TFGMaterials.Chalk = new Material.Builder(32102, gregtechId("chalk"))
                .dust()
                .color(0xA4A39F)
                .build();

        TFGMaterials.Chert = new Material.Builder(32103, gregtechId("chert"))
                .dust()
                .color(0x7A6756)
                .build();

        TFGMaterials.Claystone = new Material.Builder(32104, gregtechId("claystone"))
                .dust()
                .color(0xAF9377)
                .build();

        TFGMaterials.Conglomerate = new Material.Builder(32105, gregtechId("conglomerate"))
                .dust()
                .color(0xA3977F)
                .build();

        TFGMaterials.Dacite = new Material.Builder(32106, gregtechId("dacite"))
                .dust()
                .color(0x979797)
                .build();

        TFGMaterials.Dolomite = new Material.Builder(32107, gregtechId("dolomite"))
                .dust()
                .color(0x515155)
                .build();

        TFGMaterials.Gabbro = new Material.Builder(32108, gregtechId("gabbro"))
                .dust()
                .color(0x7F8081)
                .build();

        TFGMaterials.Gneiss = new Material.Builder(32109, gregtechId("gneiss"))
                .dust()
                .color(0x6A6D60)
                .build();

        TFGMaterials.Limestone = new Material.Builder(32111, gregtechId("limestone"))
                .dust()
                .color(0xA09885)
                .build();

        TFGMaterials.Phyllite = new Material.Builder(32115, gregtechId("phyllite"))
                .dust()
                .color(0x706B69)
                .build();

        TFGMaterials.Rhyolite = new Material.Builder(32117, gregtechId("rhyolite"))
                .dust()
                .color(0x726D69)
                .build();


        TFGMaterials.Schist = new Material.Builder(32119, gregtechId("schist"))
                .dust()
                .color(0x6E735C)
                .build();

        TFGMaterials.Shale = new Material.Builder(32120, gregtechId("shale"))
                .dust()
                .color(0x686567)
                .build();


        TFGMaterials.Slate = new Material.Builder(32122, gregtechId("slate"))
                .dust()
                .color(0x989287)
                .build();

        Materials.Copper.setProperty(PropertyKey.TOOL, new ToolProperty());

        Copper.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1080, 0.35f));
        Bismuth.setProperty(TFGPropertyKey.HEAT, new HeatProperty(270, 0.35F));
        Brass.setProperty(TFGPropertyKey.HEAT, new HeatProperty(930, 0.35F));
        Lead.setProperty(TFGPropertyKey.HEAT, new HeatProperty(328, 0.22F));
        Nickel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1453, 0.48F));
        RoseGold.setProperty(TFGPropertyKey.HEAT, new HeatProperty(960, 0.35F));
        Silver.setProperty(TFGPropertyKey.HEAT, new HeatProperty(961, 0.48F));
        Tin.setProperty(TFGPropertyKey.HEAT, new HeatProperty(230, 0.14F));
        Zinc.setProperty(TFGPropertyKey.HEAT, new HeatProperty(420, 0.21F));
        SterlingSilver.setProperty(TFGPropertyKey.HEAT, new HeatProperty(900, 0.35F));
        Bronze.setProperty(TFGPropertyKey.HEAT, new HeatProperty(950, 0.35F));
        BlackBronze.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1070, 0.35F));
        BismuthBronze.setProperty(TFGPropertyKey.HEAT, new HeatProperty(985, 0.35F));
        Gold.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1060, 0.6F));
        //PigIron.setProperty(TFCPropertyKey.TFC, new TFCProperty(0.35F, 3));
        //HighCarbonSteel.setProperty(TFCPropertyKey.TFC, new TFCProperty(0.35F, 3));
        WroughtIron.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1535, 0.35F));
        //HighCarbonBlackSteel.setProperty(TFCPropertyKey.TFC, new TFCProperty(0.35F, 4));
        Steel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F));
        //WeakSteel.setProperty(TFCPropertyKey.TFC, new TFCProperty(0.35F, 4));
        Platinum.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1730, 0.35F));
        BlackSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1485, 0.35F));
        //WeakBlueSteel.setProperty(TFCPropertyKey.TFC, new TFCProperty(0.35F, 5));
        //WeakRedSteel.setProperty(TFCPropertyKey.TFC, new TFCProperty(0.35F, 5));
        //HighCarbonBlueSteel.setProperty(TFCPropertyKey.TFC, new TFCProperty(0.35F, 5));
        //HighCarbonRedSteel.setProperty(TFCPropertyKey.TFC, new TFCProperty(0.35F, 5));
        BlueSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F));
        RedSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F));

    }
}
