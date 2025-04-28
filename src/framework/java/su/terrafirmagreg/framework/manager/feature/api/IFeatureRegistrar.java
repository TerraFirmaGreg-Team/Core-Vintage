package su.terrafirmagreg.framework.manager.feature.api;

public interface IFeatureRegistrar {

  <T extends IFeature> void addFeature(T feature);
}
