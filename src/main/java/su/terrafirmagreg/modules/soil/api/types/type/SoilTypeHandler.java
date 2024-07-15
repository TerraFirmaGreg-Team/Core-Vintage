package su.terrafirmagreg.modules.soil.api.types.type;

public final class SoilTypeHandler {

    public static void init() {

        SoilTypes.SILT = SoilType
                .builder("silt")
                .build();

        SoilTypes.LOAM = SoilType
                .builder("loam")
                .build();

        SoilTypes.SANDY_LOAM = SoilType
                .builder("sandy_loam")
                .build();

        SoilTypes.SILTY_LOAM = SoilType
                .builder("silty_loam")
                .build();

        SoilTypes.HUMUS = SoilType
                .builder("humus")
                .build();
    }
}
