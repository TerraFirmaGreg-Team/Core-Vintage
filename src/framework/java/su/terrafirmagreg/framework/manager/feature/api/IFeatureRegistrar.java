package su.terrafirmagreg.framework.manager.feature.api;

public interface IFeatureRegistrar {

  <T extends IFeatureEntry> void addFeature(T feature);
}
