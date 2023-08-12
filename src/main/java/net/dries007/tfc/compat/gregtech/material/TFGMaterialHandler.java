package net.dries007.tfc.compat.gregtech.material;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.NO_UNIFICATION;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.unification.material.properties.PropertyKey.TOOL;
import static gregtech.api.util.GTUtility.gregtechId;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags.*;
import static net.dries007.tfc.compat.gregtech.material.TFGMaterials.*;
import static net.dries007.tfc.compat.gregtech.material.TFGPropertyKey.HEAT;

public class TFGMaterialHandler {

    public static void init() {

        // Metals
        Unknown = new Material.Builder(32000, gregtechId("unknown"))
                .ingot().fluid()
                .color(0x2F2B27).iconSet(METALLIC)
                .fluidTemp(1250)
                .flags(NO_UNIFICATION)
                .build();

        PigIron = new Material.Builder(32001, gregtechId("pig_iron"))
                .ingot().fluid()
                .color(0x6A595C).iconSet(METALLIC)
                .fluidTemp(1535)
                .build();

        HighCarbonSteel = new Material.Builder(32002, gregtechId("high_carbon_steel"))
                .ingot().fluid()
                .color(0x5F5F5F).iconSet(METALLIC)
                .fluidTemp(1540)
                .build();

        HighCarbonBlackSteel = new Material.Builder(32003, gregtechId("high_carbon_black_steel"))
                .ingot().fluid()
                .color(0x111111).iconSet(METALLIC)
                .fluidTemp(1540)
                .build();

        HighCarbonRedSteel = new Material.Builder(32004, gregtechId("high_carbon_red_steel"))
                .ingot().fluid()
                .color(0x700503).iconSet(METALLIC)
                .fluidTemp(1540)
                .build();

        HighCarbonBlueSteel = new Material.Builder(32005, gregtechId("high_carbon_blue_steel"))
                .ingot().fluid()
                .color(0x2D5596).iconSet(METALLIC)
                .fluidTemp(1540)
                .build();

        WeakSteel = new Material.Builder(32006, gregtechId("weak_steel"))
                .ingot().fluid()
                .color(0x111111).iconSet(METALLIC)
                .fluidTemp(1540)
                .build();

        WeakBlueSteel = new Material.Builder(32007, gregtechId("weak_blue_steel"))
                .ingot().fluid()
                .color(0x2D5596).iconSet(METALLIC)
                .fluidTemp(1540)
                .build();

        WeakRedSteel = new Material.Builder(32008, gregtechId("weak_red_steel"))
                .ingot().fluid()
                .color(0x700503).iconSet(METALLIC)
                .fluidTemp(1540)
                .build();

        // FluidTypes
        HotWater = new Material.Builder(32050, gregtechId("hot_water"))
                .color(0x345FDA)
                .build();

        FreshWater = new Material.Builder(32051, gregtechId("fresh_water"))
                .color(0x296ACD)
                .build();

        Cider = new Material.Builder(32052, gregtechId("cider"))
                .color(0XB0AE32)
                .build();

        Vodka = new Material.Builder(32053, gregtechId("vodka"))
                .color(0XDCDCDC)
                .build();

        Sake = new Material.Builder(32054, gregtechId("sake"))
                .color(0XB7D9BC)
                .build();

        CornWhiskey = new Material.Builder(32055, gregtechId("corn_whiskey"))
                .color(0XD9C7B7)
                .build();

        RyeWhiskey = new Material.Builder(32056, gregtechId("rye_whiskey"))
                .color(0XC77D51)
                .build();

        Whiskey = new Material.Builder(32057, gregtechId("whiskey"))
                .color(0X583719)
                .build();

        Beer = new Material.Builder(32058, gregtechId("beer"))
                .color(0XC39E37)
                .build();

        Rum = new Material.Builder(32059, gregtechId("rum"))
                .color(0X6E0123)
                .build();

        Limewater = new Material.Builder(32060, gregtechId("limewater"))
                .color(0XB4B4B4)
                .build();

        Tannin = new Material.Builder(32061, gregtechId("tannin"))
                .color(0X63594E)
                .build();

        Vinegar = new Material.Builder(32062, gregtechId("vinegar"))
                .color(0XC7C2AA)
                .build();

        Brine = new Material.Builder(32063, gregtechId("brine"))
                .color(0XDCD3C9)
                .build();

        CurdledMilk = new Material.Builder(32064, gregtechId("curdled_milk"))
                .color(0XFFFBE8)
                .build();

        MilkVinegar = new Material.Builder(32065, gregtechId("milk_vinegar"))
                .color(0XFFFBE8)
                .build();

        OliveOil = new Material.Builder(32066, gregtechId("olive_oil"))
                .color(0X6A7537)
                .build();

        OliveOilWater = new Material.Builder(32067, gregtechId("olive_oil_water"))
                .color(0X4A4702)
                .build();

        Lye = new Material.Builder(32068, gregtechId("lye"))
                .color(0XFEFFDE)
                .build();

        // StoneTypes
        Chalk = new Material.Builder(32102, gregtechId("chalk"))
                .dust()
                .color(0xA4A39F)
                .build();

        Chert = new Material.Builder(32103, gregtechId("chert"))
                .dust()
                .color(0x7A6756)
                .build();

        Claystone = new Material.Builder(32104, gregtechId("claystone"))
                .dust()
                .color(0xAF9377)
                .build();

        Conglomerate = new Material.Builder(32105, gregtechId("conglomerate"))
                .dust()
                .color(0xA3977F)
                .build();

        Dacite = new Material.Builder(32106, gregtechId("dacite"))
                .dust()
                .color(0x979797)
                .build();

        Dolomite = new Material.Builder(32107, gregtechId("dolomite"))
                .dust()
                .color(0x515155)
                .build();

        Gabbro = new Material.Builder(32108, gregtechId("gabbro"))
                .dust()
                .color(0x7F8081)
                .build();

        Gneiss = new Material.Builder(32109, gregtechId("gneiss"))
                .dust()
                .color(0x6A6D60)
                .build();

        Limestone = new Material.Builder(32111, gregtechId("limestone"))
                .dust()
                .color(0xA09885)
                .build();

        Phyllite = new Material.Builder(32115, gregtechId("phyllite"))
                .dust()
                .color(0x706B69)
                .build();

        Rhyolite = new Material.Builder(32117, gregtechId("rhyolite"))
                .dust()
                .color(0x726D69)
                .build();


        Schist = new Material.Builder(32119, gregtechId("schist"))
                .dust()
                .color(0x6E735C)
                .build();

        Shale = new Material.Builder(32120, gregtechId("shale"))
                .dust()
                .color(0x686567)
                .build();

        Slate = new Material.Builder(32122, gregtechId("slate"))
                .dust()
                .color(0x989287)
                .build();

        // Some custom properties for materials
        SaltWater.setMaterialRGB(0x1F5099);

        Stone.setProperty(TOOL, new ToolProperty(1.0f, 1f, 30, 1));
        Copper.setProperty(TOOL, new ToolProperty(1.5f, 2f, 88, 2));
        BismuthBronze.setProperty(TOOL, new ToolProperty(1.8f, 2f, 174, 2));
        BlackBronze.setProperty(TOOL, new ToolProperty(2.2f, 2f, 212, 2));

        Copper.setProperty(HEAT, new HeatProperty(1080, 0.35f, 1));
        Bismuth.setProperty(HEAT, new HeatProperty(270, 0.35F, 1));
        Brass.setProperty(HEAT, new HeatProperty(930, 0.35F, 1));
        Lead.setProperty(HEAT, new HeatProperty(328, 0.22F, 1));
        Nickel.setProperty(HEAT, new HeatProperty(1453, 0.48F, 1));
        RoseGold.setProperty(HEAT, new HeatProperty(960, 0.35F, 1));
        Silver.setProperty(HEAT, new HeatProperty(961, 0.48F, 1));
        Tin.setProperty(HEAT, new HeatProperty(230, 0.14F, 1));
        Zinc.setProperty(HEAT, new HeatProperty(420, 0.21F, 1));
        SterlingSilver.setProperty(HEAT, new HeatProperty(900, 0.35F, 1));
        Bronze.setProperty(HEAT, new HeatProperty(950, 0.35F, 2));
        BlackBronze.setProperty(HEAT, new HeatProperty(1070, 0.35F, 2));
        BismuthBronze.setProperty(HEAT, new HeatProperty(985, 0.35F, 2));
        Gold.setProperty(HEAT, new HeatProperty(1060, 0.6F, 1));
        PigIron.setProperty(HEAT, new HeatProperty(1535, 0.35F, 3));
        HighCarbonSteel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 3));
        WroughtIron.setProperty(HEAT, new HeatProperty(1535, 0.35F, 3));
        HighCarbonBlackSteel.setProperty(HEAT, new HeatProperty(0.35F, 1540, 6));
        Steel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 4));
        WeakSteel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 4));
        Platinum.setProperty(HEAT, new HeatProperty(1730, 0.35F, 5));
        BlackSteel.setProperty(HEAT, new HeatProperty(1485, 0.35F, 5));
        WeakBlueSteel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 5));
        WeakRedSteel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 5));
        HighCarbonBlueSteel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 5));
        HighCarbonRedSteel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 5));
        BlueSteel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 6));
        RedSteel.setProperty(HEAT, new HeatProperty(1540, 0.35F, 6));
        Unknown.setProperty(HEAT, new HeatProperty(1250, 0.3f, 1));
        Iron.setProperty(HEAT, new HeatProperty(1535, 0.35F, 3));

        Copper.addFlags(GENERATE_ANVIL);
        BismuthBronze.addFlags(GENERATE_ANVIL);
        Bronze.addFlags(GENERATE_ANVIL);
        BlackBronze.addFlags(GENERATE_ANVIL);
        BlackSteel.addFlags(GENERATE_ANVIL);
        Iron.addFlags(GENERATE_ANVIL);
        Steel.addFlags(GENERATE_ANVIL);
        BlackSteel.addFlags(GENERATE_ANVIL);
        RedSteel.addFlags(GENERATE_ANVIL);
        BlueSteel.addFlags(GENERATE_ANVIL);

        Copper.addFlags(TOOL_MATERIAL_CAN_BE_UNMOLDED);
        BismuthBronze.addFlags(TOOL_MATERIAL_CAN_BE_UNMOLDED);
        Bronze.addFlags(TOOL_MATERIAL_CAN_BE_UNMOLDED);
        BlackBronze.addFlags(TOOL_MATERIAL_CAN_BE_UNMOLDED);

        Unknown.addFlags(UNUSABLE);
        PigIron.addFlags(UNUSABLE);
        HighCarbonSteel.addFlags(UNUSABLE);
        HighCarbonBlackSteel.addFlags(UNUSABLE);
        HighCarbonBlueSteel.addFlags(UNUSABLE);
        HighCarbonRedSteel.addFlags(UNUSABLE);
        WeakSteel.addFlags(UNUSABLE);
        WeakRedSteel.addFlags(UNUSABLE);
        WeakBlueSteel.addFlags(UNUSABLE);
    }
}
