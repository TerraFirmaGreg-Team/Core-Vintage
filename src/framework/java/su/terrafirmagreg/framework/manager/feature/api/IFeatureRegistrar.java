package su.terrafirmagreg.framework.manager.feature.api;

public interface IFeatureRegistrar {

  <F extends IFeatureEntry> void addFeature(F feature);


}
