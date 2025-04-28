package su.terrafirmagreg.framework.manager.feature.api;

import su.terrafirmagreg.framework.manager.feature.FeatureMap;
import su.terrafirmagreg.framework.module.api.IModule;

public interface IFeatureManager {

  IModule getModule();

  FeatureMap getMap();

  IFeatureRegistrar getRegistrar();

  IFeatureService getService();

}
