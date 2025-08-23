package su.terrafirmagreg.modules.core.plugin.gregtech.unification.material.materials;

import su.terrafirmagreg.framework.manager.plugin.spi.gregtech.unification.material.BuilderMaterial;
import su.terrafirmagreg.modules.core.plugin.gregtech.unification.material.MaterialsCore;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconSet;

import static gregtech.api.unification.material.Materials.Carbon;
import static gregtech.api.unification.material.Materials.Oxygen;
import static gregtech.api.unification.material.Materials.Redstone;
import static gregtech.api.unification.material.Materials.Silicon;
import static gregtech.api.unification.material.info.MaterialFlags.CRYSTALLIZABLE;
import static gregtech.api.unification.material.info.MaterialFlags.DECOMPOSITION_BY_ELECTROLYZING;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_DENSE;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_LENS;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.info.MaterialFlags.NO_UNIFICATION;
import static gregtech.api.unification.material.info.MaterialIconSet.DIAMOND;

public class SecondDegreeMaterialsCore {

  public static void init() {

    MaterialsCore.DryIce = new BuilderMaterial("dry_ice")
      .dust()
      .liquid(new FluidBuilder().temperature(293))
      .color(0xB4CAD6)
      .iconSet(DIAMOND)
      .flags(DECOMPOSITION_BY_ELECTROLYZING)
      .components(Carbon, 1, Oxygen, 2)
      .build();

    MaterialsCore.Fluix = new BuilderMaterial("fluix")
      .dust()
      .liquid(new FluidBuilder().temperature(1200))
      .color(0x674FAF)
      .iconSet(MaterialIconSet.QUARTZ)
      .flags(GENERATE_LENS, GENERATE_PLATE, DECOMPOSITION_BY_ELECTROLYZING, CRYSTALLIZABLE)
      .components(Silicon, 2, Oxygen, 4, Redstone, 1)
      .build();

    MaterialsCore.ChargedCertusQuartz = new BuilderMaterial("charged_certus_quartz")
      .liquid(new FluidBuilder().temperature(1200))
      .dust()
      .color(0xCFDAFF)
      .iconSet(MaterialIconSet.QUARTZ)
      .flags(GENERATE_LENS, GENERATE_PLATE, DECOMPOSITION_BY_ELECTROLYZING, CRYSTALLIZABLE)
      .components(Silicon, 1, Oxygen, 2)
      .build();

    MaterialsCore.Desh = new BuilderMaterial("desh")
      .dust()
      .liquid(new FluidBuilder().temperature(2200))
      .color(0x2b2d31)
      .iconSet(MaterialIconSet.FLINT)
      .ore()
      .flags(GENERATE_PLATE, GENERATE_DENSE)
      .build();

    MaterialsCore.DenseIce = new BuilderMaterial("dense_ice")
      .dust()
      .liquid(new FluidBuilder())
      .color(0x5c7297)
      .flags(GENERATE_PLATE, GENERATE_DENSE)
      .build();

    MaterialsCore.MeteoricIron = new BuilderMaterial("meteoric_iron")
      .dust()
      .liquid(new FluidBuilder().temperature(2200))
      .color(0x40311d)
      .flags(GENERATE_PLATE, GENERATE_DENSE)
      .build();

    // Metals
    MaterialsCore.Unknown = new BuilderMaterial("unknown")
      .ingot()
      .liquid(new FluidBuilder().temperature(1250))
      .color(0x2F2B27).iconSet(MaterialIconSet.METALLIC)
      .flags(MaterialFlags.NO_UNIFICATION)
      .build();

    MaterialsCore.PigIron = new BuilderMaterial("pig_iron")
      .ingot()
      .liquid(new FluidBuilder().temperature(1535))
      .color(0x6A595C).iconSet(MaterialIconSet.METALLIC)
      .build();

    MaterialsCore.HighCarbonSteel = new BuilderMaterial("high_carbon_steel")
      .ingot().liquid(new FluidBuilder().temperature(1540))
      .color(0x5F5F5F).iconSet(MaterialIconSet.METALLIC)
      .build();

    MaterialsCore.HighCarbonBlackSteel = new BuilderMaterial("high_carbon_black_steel")
      .ingot().liquid(new FluidBuilder().temperature(1540))
      .color(0x111111).iconSet(MaterialIconSet.METALLIC)
      .build();

    MaterialsCore.HighCarbonRedSteel = new BuilderMaterial("high_carbon_red_steel")
      .ingot().liquid(new FluidBuilder().temperature(1540))
      .color(0x700503).iconSet(MaterialIconSet.METALLIC)
      .build();

    MaterialsCore.HighCarbonBlueSteel = new BuilderMaterial("high_carbon_blue_steel")
      .ingot().liquid(new FluidBuilder().temperature(1540))
      .color(0x2D5596).iconSet(MaterialIconSet.METALLIC)
      .build();

    MaterialsCore.WeakSteel = new BuilderMaterial("weak_steel")
      .ingot().liquid(new FluidBuilder().temperature(1540))
      .color(0x111111).iconSet(MaterialIconSet.METALLIC)
      .build();

    MaterialsCore.WeakBlueSteel = new BuilderMaterial("weak_blue_steel")
      .ingot().liquid(new FluidBuilder().temperature(1540))
      .color(0x2D5596).iconSet(MaterialIconSet.METALLIC)
      .build();

    MaterialsCore.WeakRedSteel = new BuilderMaterial("weak_red_steel")
      .ingot().liquid(new FluidBuilder().temperature(1540))
      .color(0x700503).iconSet(MaterialIconSet.METALLIC)
      .build();

    // - Породы планет
    // Mercury
    new BuilderMaterial("stone_mercury")
      .dust()
      .color(0x727272)
      .build();

    // Venus
    new BuilderMaterial("stone_venus")
      .dust()
      .color(0xA86540)
      .build();

    // Moon
    new BuilderMaterial("stone_moon")
      .dust()
      .color(0xFFFFFF)
      .build();

    // Mars
    new BuilderMaterial("stone_mars")
      .dust()
      .color(0x431401)
      .build();

    // Phobos
    new BuilderMaterial("stone_phobos")
      .dust()
      .color(0x8c7965)
      .build();

    // Deimos
    new BuilderMaterial("stone_deimos")
      .dust()
      .color(0xcbb48c)
      .build();

    // Ceres
    new BuilderMaterial("stone_ceres")
      .dust()
      .color(0x757573)
      .build();

    // Asteroids
    new BuilderMaterial("stone_asteroid_black")
      .dust()
      .color(0x2f2d2a)
      .build();
    new BuilderMaterial("stone_asteroid_gray")
      .dust()
      .color(0x3a3a3a)
      .build();
    new BuilderMaterial("stone_asteroid_lightgray")
      .dust()
      .color(0x535250)
      .build();

    // Jupiter
    new BuilderMaterial("stone_jupiter")
      .dust()
      .color(0xfdc890)
      .build();

    // IO
    new BuilderMaterial("stone_io")
      .dust()
      .color(0xac8033)
      .build();

    // Europa
    new BuilderMaterial("stone_europa")
      .dust()
      .color(0x767069)
      .build();

    // Ganymede
    new BuilderMaterial("stone_ganymede")
      .dust()
      .color(0x5d5851)
      .build();

    // Callisto
    new BuilderMaterial("stone_callisto")
      .dust()
      .color(0x6a5447)
      .build();

    // Saturn
    new BuilderMaterial("stone_saturn")
      .dust()
      .color(0xb57b51)
      .build();

    // Rhea
    new BuilderMaterial("stone_rhea")
      .dust()
      .color(0x6c6c6c)
      .build();

    // Titan
    new BuilderMaterial("stone_titan")
      .dust()
      .color(0x584737)
      .build();

    // Iapetus
    new BuilderMaterial("stone_iapetus")
      .dust()
      .color(0x4c371f)
      .build();

    // Uranus
    new BuilderMaterial("stone_uranus")
      .dust()
      .color(0xa7c6f6)
      .build();

    // Oberon
    new BuilderMaterial("stone_oberon")
      .dust()
      .color(0x78667b)
      .build();

    // Neptune
    new BuilderMaterial("stone_neptune")
      .dust()
      .color(0x226ea1)
      .build();

    // Triton
    new BuilderMaterial("stone_triton")
      .dust()
      .color(0x807a80)
      .build();

    // Pluto
    new BuilderMaterial("stone_pluto")
      .dust()
      .color(0x757674)
      .build();

    // Eris
    new BuilderMaterial("stone_eris")
      .dust()
      .color(0xc1c1bd)
      .build();

    // - Атмосферы планет
    // Mercury
    new BuilderMaterial("mercury_air")
      .gas()
      .color(0x8E8E8E)
      .build();
    new BuilderMaterial("mercury_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x8E8E8E)
      .build();

    // Venus
    new BuilderMaterial("venus_air")
      .gas()
      .color(0xAC815C)
      .build();
    new BuilderMaterial("venus_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xAC815C)
      .build();

    // Moon
    new BuilderMaterial("moon_air")
      .gas()
      .color(0xBEC1B6)
      .build();
    new BuilderMaterial("moon_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xBEC1B6)
      .build();

    // Mars
    new BuilderMaterial("mars_air")
      .gas()
      .color(0x9D5C4B)
      .build();
    new BuilderMaterial("mars_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x9D5C4B)
      .build();

    // Jupiter
    new BuilderMaterial("jupiter_air")
      .gas()
      .color(0x9F7652)
      .build();
    new BuilderMaterial("jupiter_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x9F7652)
      .build();

    // IO
    new BuilderMaterial("io_air")
      .gas()
      .color(0xDED866)
      .build();
    new BuilderMaterial("io_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xDED866)
      .build();

    // Europa
    new BuilderMaterial("europa_air")
      .gas()
      .color(0xAA907C)
      .build();
    new BuilderMaterial("europa_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xAA907C)
      .build();

    // Callisto
    new BuilderMaterial("callisto_air")
      .gas()
      .color(0x736655)
      .build();
    new BuilderMaterial("callisto_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x736655)
      .build();

    // Saturn
    new BuilderMaterial("saturn_air")
      .gas()
      .color(0xF1D88C)
      .build();
    new BuilderMaterial("saturn_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xF1D88C)
      .build();

    // Titan
    new BuilderMaterial("titan_air")
      .gas()
      .color(0x4F7755)
      .build();
    new BuilderMaterial("titan_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x4F7755)
      .build();

    // Uran
    new BuilderMaterial("uranus_air")
      .gas()
      .color(0xA0EAF5)
      .build();
    new BuilderMaterial("uranus_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xA0EAF5)
      .build();

    // Neptune
    new BuilderMaterial("neptune_air")
      .gas()
      .color(0x6793E3)
      .build();
    new BuilderMaterial("neptune_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0x6793E3)
      .build();

    // Triton
    new BuilderMaterial("triton_air")
      .gas()
      .color(0xCCA9bA)
      .build();
    new BuilderMaterial("triton_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xCCA9bA)
      .build();

    // Pluto
    new BuilderMaterial("pluto_air")
      .gas()
      .color(0xCDA787)
      .build();
    new BuilderMaterial("pluto_liquid_air")
      .liquid(new FluidBuilder().temperature(32))
      .flags(NO_UNIFICATION)
      .color(0xCDA787)
      .build();
  }

}
