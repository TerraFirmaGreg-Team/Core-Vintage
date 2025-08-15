package su.terrafirmagreg.framework.manager.feature.api;

import su.terrafirmagreg.framework.manager.api.IBaseRegistrar;

public interface IFeatureRegistrar extends IBaseRegistrar<IFeatureEntry> {

  <E extends IFeatureEntry> void addFeature(E entry);


}
