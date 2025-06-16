package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.wood.feature.woodtype.FeatureWoodType;

public class FeaturesWood {

  public static void onRegister(IFeatureRegistrar registrar) {
    registrar.addFeature(new FeatureWoodType());
  }
}
