package su.terrafirmagreg.modules.soil.feature.soiltype;

import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilTypeHandler;


public class FeatureSoilType extends BaseFeature {


  public FeatureSoilType() {
    super(FeatureSettings.of()
      .registryKey("soil_type"));

    SoilTypeHandler.init();
  }


}
