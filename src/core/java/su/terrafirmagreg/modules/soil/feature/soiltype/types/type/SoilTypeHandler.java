package su.terrafirmagreg.modules.soil.feature.soiltype.types.type;

public class SoilTypeHandler {

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
