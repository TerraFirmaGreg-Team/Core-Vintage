package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import lombok.Getter;

@Getter
public class PluginRegistrar implements IPluginRegistrar {

  private final IPluginManager manager;
  private final IModule module;
  private final PluginMap map;

  public PluginRegistrar(IPluginManager manager) {

    this.manager = manager;
    this.module = manager.getModule();
    this.map = manager.getMap();
  }


  @Override
  public <T extends IPluginEntry> void addPlugin(T plugin) {
    var pluginClass = plugin.getClass();
    var settings = plugin.getSettings();

    if (!settings.isEnabled()) {
      manager.getLogger().debug("Plugin {} is disabled: {}", pluginClass.getSimpleName());
      return;
    }

    if (settings.isHasSubscriptions()) {
      MinecraftForge.EVENT_BUS.register(pluginClass);
    }

    this.map.put(pluginClass, plugin);
  }
}
