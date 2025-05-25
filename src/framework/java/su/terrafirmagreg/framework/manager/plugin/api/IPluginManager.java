package su.terrafirmagreg.framework.manager.plugin.api;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.plugin.PluginMap;
import su.terrafirmagreg.framework.module.api.IModule;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IPluginManager {

  Map<IModule, IPluginManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  IModule getModule();

  PluginMap getMap();

  IPluginRegistrar getRegistrar();

  IPluginService getService();

  LoggingHelper getLogger();
}
