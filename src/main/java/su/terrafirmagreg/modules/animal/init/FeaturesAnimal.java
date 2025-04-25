package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.animal.feature.mapping.RemappingAnimal;

public final class FeaturesAnimal {

  public static void onRegister(IFeatureRegistrar registrar) {

    registrar.addFeature(new RemappingAnimal());
  }
}
