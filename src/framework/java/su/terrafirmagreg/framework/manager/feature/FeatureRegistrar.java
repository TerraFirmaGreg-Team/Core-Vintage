package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.FeatureMap.FeatureWrapper;
import su.terrafirmagreg.framework.manager.feature.api.IFeature;
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
  public <T extends IFeature> void addFeature(T feature) {
    var featureClass = feature.getClass();

    if (!feature.isEnabled()) {
      module.getLogger().debug("Feature {} is disabled: {}", featureClass.getSimpleName());
      return;
    }

    if (feature.hasSubscriptions()) {
      MinecraftForge.EVENT_BUS.register(featureClass);
    }

    this.map.put(featureClass, FeatureWrapper.of(feature));
  }
}
