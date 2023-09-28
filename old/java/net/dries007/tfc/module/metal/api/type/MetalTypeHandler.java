package net.dries007.tfc.module.metal.api.type;

import static gregtech.api.unification.material.Materials.*;
import static net.dries007.tfc.module.metal.api.type.MetalTypes.*;

public class MetalTypeHandler {
    public static void init() {
        COPPER = new MetalType("copper", Copper);
        BISMUTH_BRONZE = new MetalType("bismuth_bronze", BismuthBronze);
        BRONZE = new MetalType("bronze", Bronze);
        BLACK_BRONZE = new MetalType("black_bronze", BlackBronze);
        BLACK_STEEL = new MetalType("black_steel", BlackSteel);
        STEEL = new MetalType("steel", Steel);
        RED_STEEL = new MetalType("red_steel", RedSteel);
        BLUE_STEEL = new MetalType("blue_steel", BlueSteel);
        WROUGHT_IRON = new MetalType("wrought_iron", WroughtIron);
    }
}
