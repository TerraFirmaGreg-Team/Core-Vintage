package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class FeatureMap extends Object2ObjectOpenHashMap<Class<? extends IFeatureEntry>, IFeatureEntry> {


  public static FeatureMap of() {
    return new FeatureMap();
  }
  
}
