package su.terrafirmagreg.framework.manager.feature.base;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry;

import lombok.Getter;

@Getter
public abstract class BaseFeature implements IFeatureEntry {

  protected final FeatureSettings settings;

  protected BaseFeature() {
    this(FeatureSettings.of());

  }

  protected BaseFeature(FeatureSettings settings) {
    this.settings = settings;

  }

}
