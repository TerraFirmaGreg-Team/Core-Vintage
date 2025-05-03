package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.device.feature.mapping.RemappingDevice;
import su.terrafirmagreg.modules.device.feature.sharpness.FeatureSharpness;

public final class FeaturesDevice {

  public static void onRegister(IFeatureRegistrar registrar) {

    registrar.addFeature(new RemappingDevice());
    registrar.addFeature(new FeatureSharpness());
  }
}
