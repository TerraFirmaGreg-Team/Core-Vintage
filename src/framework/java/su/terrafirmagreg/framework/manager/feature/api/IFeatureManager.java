package su.terrafirmagreg.framework.manager.feature.api;

import su.terrafirmagreg.framework.manager.feature.FeatureMap;
import su.terrafirmagreg.framework.module.api.IModule;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IFeatureManager {

  Map<IModule, IFeatureManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  IModule getModule();

  FeatureMap getMap();

  IFeatureRegistrar getRegistrar();

  IFeatureService getService();

}
