package su.terrafirmagreg.modules.soil.feature.soiltype;

import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilTypes;


public class FeatureSoilType extends BaseFeature {

  static {

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
