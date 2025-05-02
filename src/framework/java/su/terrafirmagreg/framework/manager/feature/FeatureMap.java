package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.FeatureMap.FeatureWrapper;
import su.terrafirmagreg.framework.manager.feature.api.IFeature;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

public class FeatureMap extends Object2ObjectOpenHashMap<Class<? extends IFeature>, FeatureWrapper> {


  public static FeatureMap of() {
    return new FeatureMap();
  }

  @Data(staticConstructor = "of")
  public static class FeatureWrapper {

    private final IFeature feature;

  }
}
