package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureService;
import su.terrafirmagreg.framework.module.api.IModule;

import lombok.Getter;

@Getter
public class FeatureService implements IFeatureService {

  private final IModule module;
  private final FeatureMap map;

  public FeatureService(FeatureManager manager) {
    this.module = manager.getModule();
    this.map = manager.getMap();
  }
}
