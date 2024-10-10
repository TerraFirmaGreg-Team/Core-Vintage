package su.terrafirmagreg.core.modules.gregtech.material;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.OreProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregicality.multiblocks.api.unification.GCYMMaterials.Trinaquadalloy;
import static gregtech.api.unification.material.Materials.Aluminium;
import static gregtech.api.unification.material.Materials.Americium;
import static gregtech.api.unification.material.Materials.Antimony;
import static gregtech.api.unification.material.Materials.Arsenic;
import static gregtech.api.unification.material.Materials.Bismuth;
import static gregtech.api.unification.material.Materials.BismuthBronze;
import static gregtech.api.unification.material.Materials.BlackBronze;
import static gregtech.api.unification.material.Materials.BlackSteel;
import static gregtech.api.unification.material.Materials.BlueSteel;
import static gregtech.api.unification.material.Materials.Borax;
import static gregtech.api.unification.material.Materials.Brass;
import static gregtech.api.unification.material.Materials.Bronze;
import static gregtech.api.unification.material.Materials.Cadmium;
import static gregtech.api.unification.material.Materials.Caesium;
import static gregtech.api.unification.material.Materials.Carbon;
import static gregtech.api.unification.material.Materials.Cerium;
import static gregtech.api.unification.material.Materials.CertusQuartz;
import static gregtech.api.unification.material.Materials.Chrome;
import static gregtech.api.unification.material.Materials.Copper;
import static gregtech.api.unification.material.Materials.Darmstadtium;
import static gregtech.api.unification.material.Materials.Electrotine;
import static gregtech.api.unification.material.Materials.EnrichedNaquadahTriniumEuropiumDuranide;
import static gregtech.api.unification.material.Materials.Europium;
import static gregtech.api.unification.material.Materials.Gallium;
import static gregtech.api.unification.material.Materials.Glowstone;
import static gregtech.api.unification.material.Materials.Gold;
import static gregtech.api.unification.material.Materials.HSSE;
import static gregtech.api.unification.material.Materials.HSSS;
import static gregtech.api.unification.material.Materials.Iridium;
import static gregtech.api.unification.material.Materials.Iron;
import static gregtech.api.unification.material.Materials.Lanthanum;
import static gregtech.api.unification.material.Materials.Lead;
import static gregtech.api.unification.material.Materials.Magnesium;
import static gregtech.api.unification.material.Materials.Manganese;
import static gregtech.api.unification.material.Materials.NaquadahEnriched;
import static gregtech.api.unification.material.Materials.Naquadria;
import static gregtech.api.unification.material.Materials.NetherQuartz;
import static gregtech.api.unification.material.Materials.Netherrack;
import static gregtech.api.unification.material.Materials.Nickel;
import static gregtech.api.unification.material.Materials.Niobium;
import static gregtech.api.unification.material.Materials.Osmiridium;
import static gregtech.api.unification.material.Materials.Osmium;
import static gregtech.api.unification.material.Materials.Oxygen;
import static gregtech.api.unification.material.Materials.Perlite;
import static gregtech.api.unification.material.Materials.Platinum;
import static gregtech.api.unification.material.Materials.RedSteel;
import static gregtech.api.unification.material.Materials.Redstone;
import static gregtech.api.unification.material.Materials.RhodiumPlatedPalladium;
import static gregtech.api.unification.material.Materials.RoseGold;
import static gregtech.api.unification.material.Materials.Ruridit;
import static gregtech.api.unification.material.Materials.Rutile;
import static gregtech.api.unification.material.Materials.SaltWater;
import static gregtech.api.unification.material.Materials.Samarium;
import static gregtech.api.unification.material.Materials.Silicon;
import static gregtech.api.unification.material.Materials.Silver;
import static gregtech.api.unification.material.Materials.StainlessSteel;
import static gregtech.api.unification.material.Materials.Steel;
import static gregtech.api.unification.material.Materials.SterlingSilver;
import static gregtech.api.unification.material.Materials.Stone;
import static gregtech.api.unification.material.Materials.Tin;
import static gregtech.api.unification.material.Materials.Titanium;
import static gregtech.api.unification.material.Materials.Trinium;
import static gregtech.api.unification.material.Materials.Tritanium;
import static gregtech.api.unification.material.Materials.Tungsten;
import static gregtech.api.unification.material.Materials.Uranium235;
import static gregtech.api.unification.material.Materials.Uranium238;
import static gregtech.api.unification.material.Materials.Uvarovite;
import static gregtech.api.unification.material.Materials.Vanadium;
import static gregtech.api.unification.material.Materials.Wheat;
import static gregtech.api.unification.material.Materials.WroughtIron;
import static gregtech.api.unification.material.Materials.Yttrium;
import static gregtech.api.unification.material.Materials.Zinc;
import static gregtech.api.unification.material.info.MaterialFlags.CRYSTALLIZABLE;
import static gregtech.api.unification.material.info.MaterialFlags.DECOMPOSITION_BY_ELECTROLYZING;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_DENSE;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_LENS;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.info.MaterialFlags.NO_UNIFICATION;
import static gregtech.api.unification.material.info.MaterialIconSet.DIAMOND;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.util.GTUtility.gregtechId;

public class TFGMaterialHandler {

  public static void init() {
    // Сухой лед
    new Material.Builder(32050, gregtechId("dry_ice"))
      .dust()
      .liquid(new FluidBuilder().temperature(293))
      .color(0xB4CAD6)
      .iconSet(DIAMOND)
      .flags(DECOMPOSITION_BY_ELECTROLYZING)
      .components(Carbon, 1, Oxygen, 2)
      .build();

    // Изменчивый кристал
    new Material.Builder(32051, gregtechId("fluix"))
      .dust()
      .liquid(new FluidBuilder().temperature(1200))
      .color(0x674FAF)
      .iconSet(MaterialIconSet.QUARTZ)
      .flags(GENERATE_LENS, GENERATE_PLATE, DECOMPOSITION_BY_ELECTROLYZING, CRYSTALLIZABLE)
      .components(Silicon, 2, Oxygen, 4, Redstone, 1)
      .build();

    // Заряженный изменчивый кристал
    new Material.Builder(32052, gregtechId("charged_certus_quartz"))
      .liquid(new FluidBuilder().temperature(1200))
      .dust()
      .color(0xCFDAFF)
      .iconSet(MaterialIconSet.QUARTZ)
      .flags(GENERATE_LENS, GENERATE_PLATE, DECOMPOSITION_BY_ELECTROLYZING, CRYSTALLIZABLE)
      .components(Silicon, 1, Oxygen, 2)
      .build();

    // Дэш
    new Material.Builder(32053, gregtechId("desh"))
      .dust()
      .liquid(new FluidBuilder().temperature(2200))
      .color(0x2b2d31)
      .iconSet(MaterialIconSet.FLINT)
      .ore()
      .flags(GENERATE_PLATE, GENERATE_DENSE)
      .build();

    // Плотный лед
    new Material.Builder(32054, gregtechId("dense_ice"))
      .dust()
      .liquid(new FluidBuilder())
      .color(0x5c7297)
      .flags(GENERATE_PLATE, GENERATE_DENSE)
      .build();

    // Метеоритное железо
    new Material.Builder(32055, gregtechId("meteoric_iron"))
      .dust()
      .liquid(new FluidBuilder().temperature(2200))
      .color(0x40311d)
      .flags(GENERATE_PLATE, GENERATE_DENSE)
      .build();

    // Custom Rocks
    TFGMaterials.Breccia = new Material.Builder(32100, gregtechId("breccia"))
      .dust()
      .color(0x706B5F)
      .build();

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

    TFGMaterials.Komatiite = new Material.Builder(32110, gregtechId("komatiite"))
      .dust()
      .color(0x586455)
      .build();

    TFGMaterials.Limestone = new Material.Builder(32111, gregtechId("limestone"))
      .dust()
      .color(0xA09885)
      .build();

    TFGMaterials.Mudstone = new Material.Builder(32112, gregtechId("mudstone"))
      .dust()
      .color(0x938E84)
      .build();

    TFGMaterials.Novaculite = new Material.Builder(32113, gregtechId("novaculite"))
      .dust()
      .color(0xADA9A1)
      .build();

    TFGMaterials.Peridotite = new Material.Builder(32114, gregtechId("peridotite"))
      .dust()
      .color(0x565F56)
      .build();

    TFGMaterials.Phyllite = new Material.Builder(32115, gregtechId("phyllite"))
      .dust()
      .color(0x706B69)
      .build();

    TFGMaterials.Porphyry = new Material.Builder(32116, gregtechId("porphyry"))
      .dust()
      .color(0x422825)
      .build();

    TFGMaterials.Rhyolite = new Material.Builder(32117, gregtechId("rhyolite"))
      .dust()
      .color(0x726D69)
      .build();

    TFGMaterials.Sandstone = new Material.Builder(32118, gregtechId("sandstone"))
      .dust()
      .color(0xBAAE90)
      .build();

    TFGMaterials.Schist = new Material.Builder(32119, gregtechId("schist"))
      .dust()
      .color(0x6E735C)
      .build();

    TFGMaterials.Shale = new Material.Builder(32120, gregtechId("shale"))
      .dust()
      .color(0x686567)
      .build();

    TFGMaterials.Siltstone = new Material.Builder(32121, gregtechId("siltstone"))
      .dust()
      .color(0xA98D79)
      .build();

    TFGMaterials.Slate = new Material.Builder(32122, gregtechId("slate"))
      .dust()
      .color(0x989287)
      .build();

    // - Породы планет
    // Mercury
    new Material.Builder(32123, gregtechId("stone_mercury"))
      .dust()
      .color(0x727272)
      .build();

    // Venus
    new Material.Builder(32124, gregtechId("stone_venus"))
      .dust()
      .color(0xA86540)
      .build();

    // Moon
    new Material.Builder(32125, gregtechId("stone_moon"))
      .dust()
      .color(0xFFFFFF)
      .build();

    // Mars
    new Material.Builder(32126, gregtechId("stone_mars"))
      .dust()
      .color(0x431401)
      .build();

    // Phobos
    new Material.Builder(32127, gregtechId("stone_phobos"))
      .dust()
      .color(0x8c7965)
      .build();

    // Deimos
    new Material.Builder(32128, gregtechId("stone_deimos"))
      .dust()
      .color(0xcbb48c)
      .build();

    // Ceres
    new Material.Builder(32129, gregtechId("stone_ceres"))
      .dust()
      .color(0x757573)
      .build();

    // Asteroids
    new Material.Builder(32130, gregtechId("stone_asteroid_black"))
      .dust()
      .color(0x2f2d2a)
      .build();
    new Material.Builder(32131, gregtechId("stone_asteroid_gray"))
      .dust()
      .color(0x3a3a3a)
      .build();
    new Material.Builder(32132, gregtechId("stone_asteroid_lightgray"))
      .dust()
      .color(0x535250)
      .build();

    // Jupiter
    new Material.Builder(32133, gregtechId("stone_jupiter"))
      .dust()
      .color(0xfdc890)
      .build();

    // IO
    new Material.Builder(32134, gregtechId("stone_io"))
      .dust()
      .color(0xac8033)
      .build();

    // Europa
    new Material.Builder(32135, gregtechId("stone_europa"))
      .dust()
      .color(0x767069)
      .build();

    // Ganymede
    new Material.Builder(32136, gregtechId("stone_ganymede"))
      .dust()
      .color(0x5d5851)
      .build();

    // Callisto
    new Material.Builder(32137, gregtechId("stone_callisto"))
      .dust()
      .color(0x6a5447)
      .build();

    // Saturn
    new Material.Builder(32138, gregtechId("stone_saturn"))
      .dust()
      .color(0xb57b51)
      .build();

    // Rhea
    new Material.Builder(32139, gregtechId("stone_rhea"))
      .dust()
      .color(0x6c6c6c)
      .build();

    // Titan
    new Material.Builder(32140, gregtechId("stone_titan"))
      .dust()
      .color(0x584737)
      .build();

    // Iapetus
    new Material.Builder(32141, gregtechId("stone_iapetus"))
      .dust()
      .color(0x4c371f)
      .build();

    // Uranus
    new Material.Builder(32142, gregtechId("stone_uranus"))
      .dust()
      .color(0xa7c6f6)
      .build();

    // Oberon
    new Material.Builder(32143, gregtechId("stone_oberon"))
      .dust()
      .color(0x78667b)
      .build();

    // Neptune
    new Material.Builder(32144, gregtechId("stone_neptune"))
      .dust()
      .color(0x226ea1)
      .build();

    // Triton
    new Material.Builder(32145, gregtechId("stone_triton"))
      .dust()
      .color(0x807a80)
      .build();

    // Pluto
    new Material.Builder(32146, gregtechId("stone_pluto"))
      .dust()
      .color(0x757674)
      .build();

    // Eris
    new Material.Builder(32147, gregtechId("stone_eris"))
      .dust()
      .color(0xc1c1bd)
      .build();

    // - Атмосферы планет
    // Mercury
    new Material.Builder(32200, gregtechId("mercury_air"))
      .gas()
      .color(0x8E8E8E)
      .build();
    new Material.Builder(32201, gregtechId("mercury_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x8E8E8E)
      .build();

    // Venus
    new Material.Builder(32202, gregtechId("venus_air"))
      .gas()
      .color(0xAC815C)
      .build();
    new Material.Builder(32203, gregtechId("venus_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xAC815C)
      .build();

    // Moon
    new Material.Builder(32204, gregtechId("moon_air"))
      .gas()
      .color(0xBEC1B6)
      .build();
    new Material.Builder(32205, gregtechId("moon_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xBEC1B6)
      .build();

    // Mars
    new Material.Builder(32206, gregtechId("mars_air"))
      .gas()
      .color(0x9D5C4B)
      .build();
    new Material.Builder(32207, gregtechId("mars_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x9D5C4B)
      .build();

    // Jupiter
    new Material.Builder(32208, gregtechId("jupiter_air"))
      .gas()
      .color(0x9F7652)
      .build();
    new Material.Builder(32209, gregtechId("jupiter_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x9F7652)
      .build();

    // IO
    new Material.Builder(32210, gregtechId("io_air"))
      .gas()
      .color(0xDED866)
      .build();
    new Material.Builder(32211, gregtechId("io_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xDED866)
      .build();

    // Europa
    new Material.Builder(32212, gregtechId("europa_air"))
      .gas()
      .color(0xAA907C)
      .build();
    new Material.Builder(32213, gregtechId("europa_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xAA907C)
      .build();

    // Callisto
    new Material.Builder(32214, gregtechId("callisto_air"))
      .gas()
      .color(0x736655)
      .build();
    new Material.Builder(32215, gregtechId("callisto_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x736655)
      .build();

    // Saturn
    new Material.Builder(32216, gregtechId("saturn_air"))
      .gas()
      .color(0xF1D88C)
      .build();
    new Material.Builder(32217, gregtechId("saturn_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xF1D88C)
      .build();

    // Titan
    new Material.Builder(32218, gregtechId("titan_air"))
      .gas()
      .color(0x4F7755)
      .build();
    new Material.Builder(32219, gregtechId("titan_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x4F7755)
      .build();

    // Uran
    new Material.Builder(32220, gregtechId("uranus_air"))
      .gas()
      .color(0xA0EAF5)
      .build();
    new Material.Builder(32221, gregtechId("uranus_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xA0EAF5)
      .build();

    // Neptune
    new Material.Builder(32222, gregtechId("neptune_air"))
      .gas()
      .color(0x6793E3)
      .build();
    new Material.Builder(32223, gregtechId("neptune_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x6793E3)
      .build();

    // Triton
    new Material.Builder(32224, gregtechId("triton_air"))
      .gas()
      .color(0xCCA9bA)
      .build();
    new Material.Builder(32225, gregtechId("triton_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xCCA9bA)
      .build();

    // Pluto
    new Material.Builder(32226, gregtechId("pluto_air"))
      .gas()
      .color(0xCDA787)
      .build();
    new Material.Builder(32227, gregtechId("pluto_liquid_air"))
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xCDA787)
      .build();


  }


  public static void postInit() {

    // Material changes

    // GTCEu
    Stone.setProperty(PropertyKey.TOOL, new ToolProperty(1.0f, 1f, 6, 1));
    Copper.setProperty(PropertyKey.TOOL, new ToolProperty(1.5f, 2f, 88, 2));
    BismuthBronze.setProperty(PropertyKey.TOOL, new ToolProperty(1.8f, 2f, 174, 2));
    Bismuth.setProperty(PropertyKey.TOOL, new ToolProperty(2.0f, 2f, 192, 2));
    BlackBronze.setProperty(PropertyKey.TOOL, new ToolProperty(2.2f, 2f, 212, 2));
    BlackSteel.setProperty(PropertyKey.TOOL, new ToolProperty(6.0f, 3f, 784, 3));

    SaltWater.setMaterialRGB(0xFF1F5099);
    Glowstone.setFormula("Au(Si(FeS2)5(CrAl2O3)Hg3)", true);
    RedSteel.addFlags(GENERATE_LONG_ROD, GENERATE_BOLT_SCREW, GENERATE_DOUBLE_PLATE);
    BlueSteel.addFlags(GENERATE_LONG_ROD, GENERATE_BOLT_SCREW, GENERATE_DOUBLE_PLATE);
    BlackSteel.addFlags(GENERATE_LONG_ROD, GENERATE_DENSE, GENERATE_BOLT_SCREW, GENERATE_DOUBLE_PLATE);
    Magnesium.addFlags(GENERATE_PLATE);
    Titanium.addFlags(GENERATE_FOIL, GENERATE_DENSE);
    Zinc.addFlags(GENERATE_LONG_ROD, GENERATE_DOUBLE_PLATE, GENERATE_GEAR, GENERATE_BOLT_SCREW, GENERATE_ROD);
    Nickel.addFlags(GENERATE_LONG_ROD, GENERATE_GEAR, GENERATE_BOLT_SCREW, GENERATE_ROD);
    Copper.addFlags(GENERATE_LONG_ROD, GENERATE_DENSE, GENERATE_GEAR, GENERATE_BOLT_SCREW);
    Bismuth.addFlags(GENERATE_LONG_ROD, GENERATE_GEAR, GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_BOLT_SCREW, GENERATE_ROD);
    BismuthBronze.addFlags(GENERATE_GEAR, GENERATE_DOUBLE_PLATE);
    CertusQuartz.addFlags(GENERATE_ROD, GENERATE_BOLT_SCREW);
    NetherQuartz.addFlags(GENERATE_ROD, GENERATE_BOLT_SCREW);
    Steel.addFlags(GENERATE_DENSE);
    Iron.addFlags(GENERATE_DENSE, GENERATE_DOUBLE_PLATE);
    Tin.addFlags(GENERATE_DENSE, GENERATE_GEAR);
    Aluminium.addFlags(GENERATE_DENSE);
    Bronze.addFlags(GENERATE_DENSE);
    Osmiridium.addFlags(GENERATE_DENSE);
    HSSS.addFlags(GENERATE_DENSE);
    StainlessSteel.addFlags(GENERATE_DENSE);
    Platinum.addFlags(GENERATE_DENSE, GENERATE_GEAR);
    Gold.addFlags(GENERATE_GEAR);
    Brass.addFlags(GENERATE_GEAR);
    Lead.addFlags(GENERATE_GEAR, GENERATE_DENSE);
    RoseGold.addFlags(GENERATE_GEAR);
    Silver.addFlags(GENERATE_GEAR);
    SterlingSilver.addFlags(GENERATE_GEAR);
    WroughtIron.addFlags(GENERATE_SMALL_GEAR, GENERATE_ROTOR, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_ROUND);
    Netherrack.addFlags(NO_UNIFICATION);
    Wheat.addFlags(NO_UNIFICATION);
    Electrotine.addFlags(GENERATE_PLATE);
    Ruridit.addFlags(GENERATE_BOLT_SCREW);
    Ruridit.getProperty(PropertyKey.FLUID).setPrimaryKey(FluidStorageKeys.LIQUID);
    Europium.addFlags(GENERATE_BOLT_SCREW);
    Americium.addFlags(GENERATE_DENSE);
    Naquadria.addFlags(GENERATE_DENSE);
    Carbon.addFlags(GENERATE_DENSE);
    Trinaquadalloy.addFlags(GENERATE_DENSE);
    EnrichedNaquadahTriniumEuropiumDuranide.addFlags(GENERATE_DENSE);
    Trinium.addFlags(GENERATE_DENSE);
    RhodiumPlatedPalladium.addFlags(GENERATE_FOIL);
    Darmstadtium.addFlags(GENERATE_FOIL);
    HSSE.addFlags(GENERATE_DOUBLE_PLATE);
    Tritanium.addFlags(GENERATE_DOUBLE_PLATE);

    CertusQuartz.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
    NetherQuartz.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
    Bismuth.setProperty(PropertyKey.ORE, new OreProperty());
    Perlite.setProperty(PropertyKey.ORE, new OreProperty());
    Uvarovite.setProperty(PropertyKey.ORE, new OreProperty());
    Manganese.setProperty(PropertyKey.ORE, new OreProperty());
    Arsenic.setProperty(PropertyKey.ORE, new OreProperty());
    Iridium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, true));
    Osmium.setProperty(PropertyKey.ORE, new OreProperty());
    Chrome.setProperty(PropertyKey.ORE, new OreProperty());
    Vanadium.setProperty(PropertyKey.ORE, new OreProperty());
    Antimony.setProperty(PropertyKey.ORE, new OreProperty());
    Rutile.setProperty(PropertyKey.ORE, new OreProperty());
    Silicon.setProperty(PropertyKey.ORE, new OreProperty());
    Uranium238.setProperty(PropertyKey.ORE, new OreProperty(1, 1, true));
    Uranium235.setProperty(PropertyKey.ORE, new OreProperty(1, 1, true));
    Niobium.setProperty(PropertyKey.ORE, new OreProperty());
    Yttrium.setProperty(PropertyKey.ORE, new OreProperty());
    Gallium.setProperty(PropertyKey.ORE, new OreProperty());
    Titanium.setProperty(PropertyKey.ORE, new OreProperty());
    Borax.setProperty(PropertyKey.ORE, new OreProperty());
    Cadmium.setProperty(PropertyKey.ORE, new OreProperty());
    Caesium.setProperty(PropertyKey.ORE, new OreProperty());
    Samarium.setProperty(PropertyKey.ORE, new OreProperty());
    Cerium.setProperty(PropertyKey.ORE, new OreProperty());
    Lanthanum.setProperty(PropertyKey.ORE, new OreProperty());
    Tungsten.setProperty(PropertyKey.ORE, new OreProperty());
    NaquadahEnriched.setProperty(PropertyKey.ORE, new OreProperty(1, 1, true));
    Glowstone.setProperty(PropertyKey.ORE, new OreProperty(1, 1, true));
  }
}
