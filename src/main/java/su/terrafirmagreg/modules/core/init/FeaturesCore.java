package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.core.feature.advanceddata.FeatureAdvancedData;
import su.terrafirmagreg.modules.core.feature.hotornot.FeatureHotOrNot;
import su.terrafirmagreg.modules.core.feature.mapping.RemappingCore;
import su.terrafirmagreg.modules.core.feature.sinkorswim.FeatureSinkOrSwim;

public final class FeaturesCore {

  public static void onRegister(IFeatureRegistrar registrar) {
    
    registrar.addFeature(new RemappingCore());
    registrar.addFeature(new FeatureAdvancedData());
    registrar.addFeature(new FeatureSinkOrSwim());
    registrar.addFeature(new FeatureHotOrNot());
  }

}
