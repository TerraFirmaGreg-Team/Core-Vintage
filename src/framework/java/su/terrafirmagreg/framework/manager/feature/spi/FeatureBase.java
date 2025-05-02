package su.terrafirmagreg.framework.manager.feature.spi;

import su.terrafirmagreg.framework.manager.feature.api.IFeature;

public abstract class FeatureBase implements IFeature {

  public boolean hasSubscriptions() {
    return true;
  }

  public boolean isEnabled() {
    return true;
  }
}
