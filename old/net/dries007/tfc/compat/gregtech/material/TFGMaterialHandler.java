package net.dries007.tfc.compat.gregtech.material;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.FORCE_GENERATE_BLOCK;
import static gregtech.api.unification.material.info.MaterialFlags.NO_UNIFICATION;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.unification.material.properties.PropertyKey.TOOL;
import static gregtech.api.util.GTUtility.gregtechId;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags.UNUSABLE;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.material.TFGPropertyKey.HEAT;

public class TFGMaterialHandler {

    public static void init() {

        // Metals
        TFGMaterials.Unknown = new Material.Builder(32000, GTUtility.gregtechId("unknown"))
                .ingot().fluid()
                .color(0x2F2B27).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1250)
                .flags(MaterialFlags.NO_UNIFICATION)
                .build();

        TFGMaterials.PigIron = new Material.Builder(32001, GTUtility.gregtechId("pig_iron"))
                .ingot().fluid()
                .color(0x6A595C).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1535)
                .build();

        TFGMaterials.HighCarbonSteel = new Material.Builder(32002, GTUtility.gregtechId("high_carbon_steel"))
                .ingot().fluid()
                .color(0x5F5F5F).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1540)
                .build();

        TFGMaterials.HighCarbonBlackSteel = new Material.Builder(32003, GTUtility.gregtechId("high_carbon_black_steel"))
                .ingot().fluid()
                .color(0x111111).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1540)
                .build();

        TFGMaterials.HighCarbonRedSteel = new Material.Builder(32004, GTUtility.gregtechId("high_carbon_red_steel"))
                .ingot().fluid()
                .color(0x700503).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1540)
                .build();

        TFGMaterials.HighCarbonBlueSteel = new Material.Builder(32005, GTUtility.gregtechId("high_carbon_blue_steel"))
                .ingot().fluid()
                .color(0x2D5596).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1540)
                .build();

        TFGMaterials.WeakSteel = new Material.Builder(32006, GTUtility.gregtechId("weak_steel"))
                .ingot().fluid()
                .color(0x111111).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1540)
                .build();

        TFGMaterials.WeakBlueSteel = new Material.Builder(32007, GTUtility.gregtechId("weak_blue_steel"))
                .ingot().fluid()
                .color(0x2D5596).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1540)
                .build();

        TFGMaterials.WeakRedSteel = new Material.Builder(32008, GTUtility.gregtechId("weak_red_steel"))
                .ingot().fluid()
                .color(0x700503).iconSet(MaterialIconSet.METALLIC)
                .fluidTemp(1540)
                .build();

        // FluidTypes
        TFGMaterials.HotWater = new Material.Builder(32050, GTUtility.gregtechId("hot_water"))
                .fluid()
                .color(0x345FDA)
                .build();

        TFGMaterials.FreshWater = new Material.Builder(32051, GTUtility.gregtechId("fresh_water"))
                .fluid()
                .color(0x296ACD)
                .build();

        TFGMaterials.Cider = new Material.Builder(32052, GTUtility.gregtechId("cider"))
                .fluid()
                .color(0XB0AE32)
                .build();

        TFGMaterials.Vodka = new Material.Builder(32053, GTUtility.gregtechId("vodka"))
                .fluid()
                .color(0XDCDCDC)
                .build();

        TFGMaterials.Sake = new Material.Builder(32054, GTUtility.gregtechId("sake"))
                .fluid()
                .color(0XB7D9BC)
                .build();

        TFGMaterials.CornWhiskey = new Material.Builder(32055, GTUtility.gregtechId("corn_whiskey"))
                .fluid()
                .color(0XD9C7B7)
                .build();

        TFGMaterials.RyeWhiskey = new Material.Builder(32056, GTUtility.gregtechId("rye_whiskey"))
                .fluid()
                .color(0XC77D51)
                .build();

        TFGMaterials.Whiskey = new Material.Builder(32057, GTUtility.gregtechId("whiskey"))
                .fluid()
                .color(0X583719)
                .build();

        TFGMaterials.Beer = new Material.Builder(32058, GTUtility.gregtechId("beer"))
                .fluid()
                .color(0XC39E37)
                .build();

        TFGMaterials.Rum = new Material.Builder(32059, GTUtility.gregtechId("rum"))
                .fluid()
                .color(0X6E0123)
                .build();

        TFGMaterials.Limewater = new Material.Builder(32060, GTUtility.gregtechId("limewater"))
                .fluid()
                .color(0XB4B4B4)
                .build();

        TFGMaterials.Tannin = new Material.Builder(32061, GTUtility.gregtechId("tannin"))
                .fluid()
                .color(0X63594E)
                .build();

        TFGMaterials.Vinegar = new Material.Builder(32062, GTUtility.gregtechId("vinegar"))
                .fluid()
                .color(0XC7C2AA)
                .build();

        TFGMaterials.Brine = new Material.Builder(32063, GTUtility.gregtechId("brine"))
                .fluid()
                .color(0XDCD3C9)
                .build();

        TFGMaterials.CurdledMilk = new Material.Builder(32064, GTUtility.gregtechId("curdled_milk"))
                .fluid()
                .color(0XFFFBE8)
                .build();

        TFGMaterials.MilkVinegar = new Material.Builder(32065, GTUtility.gregtechId("milk_vinegar"))
                .fluid()
                .color(0XFFFBE8)
                .build();

        TFGMaterials.OliveOil = new Material.Builder(32066, GTUtility.gregtechId("olive_oil"))
                .fluid()
                .color(0X6A7537)
                .build();

        TFGMaterials.OliveOilWater = new Material.Builder(32067, GTUtility.gregtechId("olive_oil_water"))
                .fluid()
                .color(0X4A4702)
                .build();

        TFGMaterials.Lye = new Material.Builder(32068, GTUtility.gregtechId("lye"))
                .fluid()
                .color(0XFEFFDE)
                .build();

        TFGMaterials.Flux = new Material.Builder(32090, GTUtility.gregtechId("flux"))
                .dust()
                .color(0XFFFBE8)
                .flags(MaterialFlags.NO_UNIFICATION)
                .build();

        // StoneTypes
        TFGMaterials.Chalk = new Material.Builder(32102, GTUtility.gregtechId("chalk"))
                .dust()
                .color(0xA4A39F)
                .build();

        TFGMaterials.Chert = new Material.Builder(32103, GTUtility.gregtechId("chert"))
                .dust()
                .color(0x7A6756)
                .build();

        TFGMaterials.Claystone = new Material.Builder(32104, GTUtility.gregtechId("claystone"))
                .dust()
                .color(0xAF9377)
                .build();

        TFGMaterials.Conglomerate = new Material.Builder(32105, GTUtility.gregtechId("conglomerate"))
                .dust()
                .color(0xA3977F)
                .build();

        TFGMaterials.Dacite = new Material.Builder(32106, GTUtility.gregtechId("dacite"))
                .dust()
                .color(0x979797)
                .build();

        TFGMaterials.Dolomite = new Material.Builder(32107, GTUtility.gregtechId("dolomite"))
                .dust()
                .color(0x515155)
                .build();

        TFGMaterials.Gabbro = new Material.Builder(32108, GTUtility.gregtechId("gabbro"))
                .dust()
                .color(0x7F8081)
                .build();

        TFGMaterials.Gneiss = new Material.Builder(32109, GTUtility.gregtechId("gneiss"))
                .dust()
                .color(0x6A6D60)
                .build();

        TFGMaterials.Limestone = new Material.Builder(32111, GTUtility.gregtechId("limestone"))
                .dust()
                .color(0xA09885)
                .build();

        TFGMaterials.Phyllite = new Material.Builder(32115, GTUtility.gregtechId("phyllite"))
                .dust()
                .color(0x706B69)
                .build();

        TFGMaterials.Rhyolite = new Material.Builder(32117, GTUtility.gregtechId("rhyolite"))
                .dust()
                .color(0x726D69)
                .build();


        TFGMaterials.Schist = new Material.Builder(32119, GTUtility.gregtechId("schist"))
                .dust()
                .color(0x6E735C)
                .build();

        TFGMaterials.Shale = new Material.Builder(32120, GTUtility.gregtechId("shale"))
                .dust()
                .color(0x686567)
                .build();

        TFGMaterials.Slate = new Material.Builder(32122, GTUtility.gregtechId("slate"))
                .dust()
                .color(0x989287)
                .build();

        // Some custom properties for materials
        Materials.SaltWater.setMaterialRGB(0xFF1F5099);

        Materials.Fireclay.addFlags(MaterialFlags.FORCE_GENERATE_BLOCK);

        Materials.Stone.setProperty(PropertyKey.TOOL, new ToolProperty(1.0f, 1f, 30, 1));
        Materials.Copper.setProperty(PropertyKey.TOOL, new ToolProperty(1.5f, 2f, 88, 2));
        Materials.BismuthBronze.setProperty(PropertyKey.TOOL, new ToolProperty(1.8f, 2f, 174, 2));
        Materials.BlackBronze.setProperty(PropertyKey.TOOL, new ToolProperty(2.2f, 2f, 212, 2));

        Materials.Copper.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1080, 0.35f, 1));
        Materials.Bismuth.setProperty(TFGPropertyKey.HEAT, new HeatProperty(270, 0.35F, 1));
        Materials.Brass.setProperty(TFGPropertyKey.HEAT, new HeatProperty(930, 0.35F, 1));
        Materials.Lead.setProperty(TFGPropertyKey.HEAT, new HeatProperty(328, 0.22F, 1));
        Materials.Nickel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1453, 0.48F, 1));
        Materials.RoseGold.setProperty(TFGPropertyKey.HEAT, new HeatProperty(960, 0.35F, 1));
        Materials.Silver.setProperty(TFGPropertyKey.HEAT, new HeatProperty(961, 0.48F, 1));
        Materials.Tin.setProperty(TFGPropertyKey.HEAT, new HeatProperty(230, 0.14F, 1));
        Materials.Zinc.setProperty(TFGPropertyKey.HEAT, new HeatProperty(420, 0.21F, 1));
        Materials.SterlingSilver.setProperty(TFGPropertyKey.HEAT, new HeatProperty(900, 0.35F, 1));
        Materials.Bronze.setProperty(TFGPropertyKey.HEAT, new HeatProperty(950, 0.35F, 2));
        Materials.BlackBronze.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1070, 0.35F, 2));
        Materials.BismuthBronze.setProperty(TFGPropertyKey.HEAT, new HeatProperty(985, 0.35F, 2));
        Materials.Gold.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1060, 0.6F, 1));
        TFGMaterials.PigIron.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1535, 0.35F, 3));
        TFGMaterials.HighCarbonSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 3));
        Materials.WroughtIron.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1535, 0.35F, 3));
        TFGMaterials.HighCarbonBlackSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(0.35F, 1540, 6));
        Materials.Steel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 4));
        TFGMaterials.WeakSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 4));
        Materials.Platinum.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1730, 0.35F, 5));
        Materials.BlackSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1485, 0.35F, 5));
        TFGMaterials.WeakBlueSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 5));
        TFGMaterials.WeakRedSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 5));
        TFGMaterials.HighCarbonBlueSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 5));
        TFGMaterials.HighCarbonRedSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 5));
        Materials.BlueSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 6));
        Materials.RedSteel.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1540, 0.35F, 6));
        TFGMaterials.Unknown.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1250, 0.3f, 1));
        Materials.Iron.setProperty(TFGPropertyKey.HEAT, new HeatProperty(1535, 0.35F, 3));

        Materials.Copper.addFlags(TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED);
        Materials.BismuthBronze.addFlags(TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED);
        Materials.Bronze.addFlags(TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED);
        Materials.BlackBronze.addFlags(TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED);

        TFGMaterials.Unknown.addFlags(TFGMaterialFlags.UNUSABLE);
        TFGMaterials.PigIron.addFlags(TFGMaterialFlags.UNUSABLE);
        TFGMaterials.HighCarbonSteel.addFlags(TFGMaterialFlags.UNUSABLE);
        TFGMaterials.HighCarbonBlackSteel.addFlags(TFGMaterialFlags.UNUSABLE);
        TFGMaterials.HighCarbonBlueSteel.addFlags(TFGMaterialFlags.UNUSABLE);
        TFGMaterials.HighCarbonRedSteel.addFlags(TFGMaterialFlags.UNUSABLE);
        TFGMaterials.WeakSteel.addFlags(TFGMaterialFlags.UNUSABLE);
        TFGMaterials.WeakRedSteel.addFlags(TFGMaterialFlags.UNUSABLE);
        TFGMaterials.WeakBlueSteel.addFlags(TFGMaterialFlags.UNUSABLE);
    }
}
