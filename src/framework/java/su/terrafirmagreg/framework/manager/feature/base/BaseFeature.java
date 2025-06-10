package su.terrafirmagreg.framework.manager.feature.base;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry;

import lombok.Getter;

@Getter
public abstract class BaseFeature implements IFeatureEntry {

  protected final Settings settings;

  protected BaseFeature() {
    this(Settings.of());

  }

  protected BaseFeature(Settings settings) {
    this.settings = settings;

  }

}
