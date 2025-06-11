package su.terrafirmagreg.modules.soil.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.soil.feature.soiltype.FeatureSoilType;

public class FeaturesSoil {

  public static void onRegister(IFeatureRegistrar registrar) {
    
    registrar.addFeature(new FeatureSoilType());
  }
}
