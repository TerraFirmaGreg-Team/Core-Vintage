package su.terrafirmagreg.framework.manager.plugin.api;

import su.terrafirmagreg.framework.manager.api.IBaseManager;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IPluginManager extends IBaseManager<IPluginEntry> {

  Map<IModuleEntry, IPluginManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  IPluginRegistrar getRegistrar();

}
