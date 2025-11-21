package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.wood.feature.mapping.FeatureRemappingWood;
import su.terrafirmagreg.modules.wood.feature.path.FeatureCreatePath;

public class FeaturesWood {

  public static void onRegister(IFeatureRegistrar registrar) {

    registrar.addFeature(new FeatureRemappingWood());
    registrar.addFeature(new FeatureCreatePath());
  }
}
