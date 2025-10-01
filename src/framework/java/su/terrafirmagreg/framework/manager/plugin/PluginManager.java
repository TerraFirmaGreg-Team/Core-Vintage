package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class PluginManager implements IPluginManager {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(PluginManager.class);

  private final IModuleEntry module;
  private final Multimap<Class<?>, IPluginEntry> mapEntry;

  private final IPluginRegistrar registrar;

  private PluginManager(IModuleEntry module) {

    this.module = module;
    this.mapEntry = LinkedHashMultimap.create();

    this.registrar = new PluginRegistrar(this);

    MinecraftForge.EVENT_BUS.register(this);
  }

  public static IPluginManager of(IModuleEntry module) {

    return MANAGER_MAP.computeIfAbsent(module, PluginManager::new);
  }

  @Override
  public FrameworkLogger getLogger() {
    return LOGGER;
  }
}
