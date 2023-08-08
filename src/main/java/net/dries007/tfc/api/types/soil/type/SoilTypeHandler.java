package net.dries007.tfc.api.types.soil.type;

public class SoilTypeHandler {
    public static void init() {
        SoilTypes.SILT = new SoilType("silt");
        SoilTypes.LOAM = new SoilType("loam");
        SoilTypes.SANDY_LOAM = new SoilType("sandy_loam");
        SoilTypes.SILTY_LOAM = new SoilType("silty_loam");
    }
}
