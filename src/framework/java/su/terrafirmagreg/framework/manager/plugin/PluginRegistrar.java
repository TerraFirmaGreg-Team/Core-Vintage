package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class PluginRegistrar implements IPluginRegistrar {

  private final IModuleEntry module;
  private final Multimap<Class<?>, IPluginEntry> mapEntry;

  public PluginRegistrar(IPluginManager manager) {

    this.module = manager.getModule();
    this.mapEntry = manager.getMapEntry();
  }


  @Override
  public <T extends IPluginEntry> void addPlugin(T entry) {
    var settings = entry.getSettings();

    if (settings.isHasSubscriptions()) {
      MinecraftForge.EVENT_BUS.register(entry.getClass());
    }

    addEntry(entry);
  }


}
