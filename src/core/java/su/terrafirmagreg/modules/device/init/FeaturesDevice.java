package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.device.feature.mapping.RemappingDevice;

public final class FeaturesDevice {

  public static void onRegister(IFeatureRegistrar registrar) {

    registrar.addFeature(new RemappingDevice());
  }
}
