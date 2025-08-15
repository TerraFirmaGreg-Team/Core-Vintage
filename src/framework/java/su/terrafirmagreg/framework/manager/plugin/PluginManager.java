package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.FeatureManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class PluginManager implements IPluginManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(FeatureManager.class);

  private final IModuleEntry module;
  private final Multimap<Class<?>, IPluginEntry> mapEntry;

  private final IPluginRegistrar registrar;

  private PluginManager(IModuleEntry module) {

    this.module = module;
    this.mapEntry = HashMultimap.create();

    this.registrar = new PluginRegistrar(this);

    MinecraftForge.EVENT_BUS.register(this);
  }

  public static synchronized IPluginManager of(IModuleEntry module) {

    return MANAGER_MAP.computeIfAbsent(module, PluginManager::new);
  }

  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
