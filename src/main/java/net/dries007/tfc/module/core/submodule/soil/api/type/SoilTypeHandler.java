package net.dries007.tfc.module.core.submodule.soil.api.type;

import static net.dries007.tfc.module.core.submodule.soil.api.type.SoilTypes.*;

public class SoilTypeHandler {
    public static void init() {
        SILT = new SoilType("silt");
        LOAM = new SoilType("loam");
        SANDY_LOAM = new SoilType("sandy_loam");
        SILTY_LOAM = new SoilType("silty_loam");
    }
}
