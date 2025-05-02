package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.animal.feature.egg.FeatureEgg;
import su.terrafirmagreg.modules.animal.feature.mapping.FeatureRemappingAnimal;

public final class FeaturesAnimal {

  public static void onRegister(IFeatureRegistrar registrar) {

    registrar.addFeature(new FeatureRemappingAnimal());
    registrar.addFeature(new FeatureEgg());
  }
}
