package su.terrafirmagreg.modules.metal.plugin.gregtech.material;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconSet;

import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.HighCarbonBlackSteel;
import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.HighCarbonBlueSteel;
import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.HighCarbonRedSteel;
import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.HighCarbonSteel;
import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.PigIron;
import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.Unknown;
import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.WeakBlueSteel;
import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.WeakRedSteel;
import static su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialsMetal.WeakSteel;

public final class MaterialMetalHandler {

  public static void init() {
    // Metals
    Unknown = new MaterialMetal.Builder("unknown")
            .ingot()
            .liquid(new FluidBuilder().temperature(1250))
            .color(0x2F2B27).iconSet(MaterialIconSet.METALLIC)
            .flags(MaterialFlags.NO_UNIFICATION)
            .build();

    PigIron = new MaterialMetal.Builder("pig_iron")
            .ingot()
            .liquid(new FluidBuilder().temperature(1535))
            .color(0x6A595C).iconSet(MaterialIconSet.METALLIC)
            .build();

    HighCarbonSteel = new MaterialMetal.Builder("high_carbon_steel")
            .ingot().liquid(new FluidBuilder().temperature(1540))
            .color(0x5F5F5F).iconSet(MaterialIconSet.METALLIC)
            .build();

    HighCarbonBlackSteel = new MaterialMetal.Builder("high_carbon_black_steel")
            .ingot().liquid(new FluidBuilder().temperature(1540))
            .color(0x111111).iconSet(MaterialIconSet.METALLIC)
            .build();

    HighCarbonRedSteel = new MaterialMetal.Builder("high_carbon_red_steel")
            .ingot().liquid(new FluidBuilder().temperature(1540))
            .color(0x700503).iconSet(MaterialIconSet.METALLIC)
            .build();

    HighCarbonBlueSteel = new MaterialMetal.Builder("high_carbon_blue_steel")
            .ingot().liquid(new FluidBuilder().temperature(1540))
            .color(0x2D5596).iconSet(MaterialIconSet.METALLIC)
            .build();

    WeakSteel = new MaterialMetal.Builder("weak_steel")
            .ingot().liquid(new FluidBuilder().temperature(1540))
            .color(0x111111).iconSet(MaterialIconSet.METALLIC)
            .build();

    WeakBlueSteel = new MaterialMetal.Builder("weak_blue_steel")
            .ingot().liquid(new FluidBuilder().temperature(1540))
            .color(0x2D5596).iconSet(MaterialIconSet.METALLIC)
            .build();

    WeakRedSteel = new MaterialMetal.Builder("weak_red_steel")
            .ingot().liquid(new FluidBuilder().temperature(1540))
            .color(0x700503).iconSet(MaterialIconSet.METALLIC)
            .build();
  }

}
