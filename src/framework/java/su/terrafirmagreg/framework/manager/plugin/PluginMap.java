package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class PluginMap extends Object2ObjectOpenHashMap<Class<? extends IPluginEntry>, IPluginEntry> {

  public static PluginMap of() {
    return new PluginMap();
  }

}
