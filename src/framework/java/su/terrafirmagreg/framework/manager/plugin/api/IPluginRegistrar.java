package su.terrafirmagreg.framework.manager.plugin.api;

import su.terrafirmagreg.framework.manager.api.IBaseRegistrar;

public interface IPluginRegistrar extends IBaseRegistrar<IPluginEntry> {

  <T extends IPluginEntry> void addPlugin(T entry);
}
