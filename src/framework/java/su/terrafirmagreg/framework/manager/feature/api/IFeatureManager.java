package su.terrafirmagreg.framework.manager.feature.api;

import su.terrafirmagreg.framework.manager.api.IBaseManager;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IFeatureManager extends IBaseManager<IFeatureEntry> {

  Map<IModuleEntry, IFeatureManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  IFeatureRegistrar getRegistrar();
}
