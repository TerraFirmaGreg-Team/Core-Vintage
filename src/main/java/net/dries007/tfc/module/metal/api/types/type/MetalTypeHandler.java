package net.dries007.tfc.module.metal.api.types.type;

import static gregtech.api.unification.material.Materials.*;

public class MetalTypeHandler {
    public static void init() {
        MetalTypes.COPPER = new MetalType("copper", Copper);
        MetalTypes.BISMUTH_BRONZE = new MetalType("bismuth_bronze", BismuthBronze);
        MetalTypes.BRONZE = new MetalType("bronze", Bronze);
        MetalTypes.BLACK_BRONZE = new MetalType("black_bronze", BlackBronze);
        MetalTypes.BLACK_STEEL = new MetalType("black_steel", BlackSteel);
        MetalTypes.STEEL = new MetalType("steel", Steel);
        MetalTypes.RED_STEEL = new MetalType("red_steel", RedSteel);
        MetalTypes.BLUE_STEEL = new MetalType("blue_steel", BlueSteel);
        MetalTypes.WROUGHT_IRON = new MetalType("wrought_iron", WroughtIron);
    }
}
