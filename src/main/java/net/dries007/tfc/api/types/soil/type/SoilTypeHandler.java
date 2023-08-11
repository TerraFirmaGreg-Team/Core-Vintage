package net.dries007.tfc.api.types.soil.type;

import static net.dries007.tfc.api.types.soil.type.SoilTypes.*;

public class SoilTypeHandler {
    public static void init() {
        SILT = new SoilType("silt");
        LOAM = new SoilType("loam");
        SANDY_LOAM = new SoilType("sandy_loam");
        SILTY_LOAM = new SoilType("silty_loam");
    }
}
