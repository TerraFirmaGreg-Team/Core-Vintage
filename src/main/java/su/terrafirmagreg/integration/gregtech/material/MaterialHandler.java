package su.terrafirmagreg.integration.gregtech.material;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.util.GTUtility;

import static su.terrafirmagreg.integration.gregtech.material.Materials.*;
import static su.terrafirmagreg.modules.core.ModuleCore.LOGGER;

public final class MaterialHandler {

    public static void init() {
        LOGGER.info("Registered materials");
        // Metals
        Unknown = new Material.Builder(32000, GTUtility.gregtechId("unknown"))
                .ingot()
                .liquid(new FluidBuilder().temperature(1250))
                .color(0x2F2B27).iconSet(MaterialIconSet.METALLIC)
                .flags(MaterialFlags.NO_UNIFICATION)
                .build();

        PigIron = new Material.Builder(32001, GTUtility.gregtechId("pig_iron"))
                .ingot()
                .liquid(new FluidBuilder().temperature(1535))
                .color(0x6A595C).iconSet(MaterialIconSet.METALLIC)
                .build();

        HighCarbonSteel = new Material.Builder(32002, GTUtility.gregtechId("high_carbon_steel"))
                .ingot().liquid(new FluidBuilder().temperature(1540))
                .color(0x5F5F5F).iconSet(MaterialIconSet.METALLIC)
                .build();

        HighCarbonBlackSteel = new Material.Builder(32003, GTUtility.gregtechId("high_carbon_black_steel"))
                .ingot().liquid(new FluidBuilder().temperature(1540))
                .color(0x111111).iconSet(MaterialIconSet.METALLIC)
                .build();

        HighCarbonRedSteel = new Material.Builder(32004, GTUtility.gregtechId("high_carbon_red_steel"))
                .ingot().liquid(new FluidBuilder().temperature(1540))
                .color(0x700503).iconSet(MaterialIconSet.METALLIC)
                .build();

        HighCarbonBlueSteel = new Material.Builder(32005, GTUtility.gregtechId("high_carbon_blue_steel"))
                .ingot().liquid(new FluidBuilder().temperature(1540))
                .color(0x2D5596).iconSet(MaterialIconSet.METALLIC)
                .build();

        WeakSteel = new Material.Builder(32006, GTUtility.gregtechId("weak_steel"))
                .ingot().liquid(new FluidBuilder().temperature(1540))
                .color(0x111111).iconSet(MaterialIconSet.METALLIC)
                .build();

        WeakBlueSteel = new Material.Builder(32007, GTUtility.gregtechId("weak_blue_steel"))
                .ingot().liquid(new FluidBuilder().temperature(1540))
                .color(0x2D5596).iconSet(MaterialIconSet.METALLIC)
                .build();

        WeakRedSteel = new Material.Builder(32008, GTUtility.gregtechId("weak_red_steel"))
                .ingot().liquid(new FluidBuilder().temperature(1540))
                .color(0x700503).iconSet(MaterialIconSet.METALLIC)
                .build();
    }

}
