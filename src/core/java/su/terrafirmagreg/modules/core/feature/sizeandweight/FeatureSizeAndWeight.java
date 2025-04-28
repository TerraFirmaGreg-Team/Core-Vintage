package su.terrafirmagreg.modules.core.feature.sizeandweight;

import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.ConfigCore;

public class FeatureSizeAndWeight extends FeatureBase {

  @Override
  public boolean isEnabled() {
    return ConfigCore.FEATURE.SIZE_OR_WEIGHT.enable;
  }
}
