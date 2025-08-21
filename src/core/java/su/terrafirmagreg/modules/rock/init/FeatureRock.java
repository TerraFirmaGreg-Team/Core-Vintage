package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.rock.feature.rocktype.FeatureRockType;

public class FeatureRock {

  public static void onRegister(IFeatureRegistrar registrar) {

    registrar.addFeature(new FeatureRockType());
  }
}
