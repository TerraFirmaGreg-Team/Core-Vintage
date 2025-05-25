package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.framework.manager.plugin.PluginMap.PluginWrapper;
import su.terrafirmagreg.framework.manager.plugin.api.IPlugin;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

public class PluginMap extends Object2ObjectOpenHashMap<Class<? extends IPlugin>, PluginWrapper> {

  public static PluginMap of() {
    return new PluginMap();
  }

  @Data(staticConstructor = "of")
  public static class PluginWrapper {

    private final IPlugin plugin;
  }
}
