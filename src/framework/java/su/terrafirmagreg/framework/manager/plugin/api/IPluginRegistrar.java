package su.terrafirmagreg.framework.manager.plugin.api;

import su.terrafirmagreg.framework.manager.plugin.PluginMap;
import su.terrafirmagreg.framework.module.api.IModule;

public interface IPluginRegistrar {

  IModule getModule();

  PluginMap getMap();

  <T extends IPluginEntry> void addPlugin(T plugin);
}
