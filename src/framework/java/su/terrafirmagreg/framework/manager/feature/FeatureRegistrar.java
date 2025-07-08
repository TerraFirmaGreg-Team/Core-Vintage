package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureManager;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import lombok.Getter;

@Getter
public class FeatureRegistrar implements IFeatureRegistrar {

  private final IModule module;
  private final FeatureMap map;

  public FeatureRegistrar(IFeatureManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();
  }

  @Override
  public void addFeature(IFeatureEntry feature) {
    var featureClass = feature.getClass();
    var settings = feature.getSettings();

    if (!settings.isEnabled()) {
      module.getLogger().debug("Feature {} is disabled: {}", featureClass.getSimpleName());
      return;
    }

    if (settings.isHasSubscriptions()) {
      MinecraftForge.EVENT_BUS.register(featureClass);
    }

    this.map.put(featureClass, feature);
  }
}
