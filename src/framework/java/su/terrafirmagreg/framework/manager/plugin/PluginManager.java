package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.FeatureManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import lombok.Getter;

@Getter
public class PluginManager implements IPluginManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(FeatureManager.class);

  private final IModule module;
  private final PluginMap map;

  private final IPluginRegistrar registrar;
  private final IPluginService service;

  private PluginManager(IModule module) {

    this.module = module;
    this.map = PluginMap.of();

    this.registrar = new PluginRegistrar(this);
    this.service = new PluginService(this);

    MinecraftForge.EVENT_BUS.register(this.service);
  }

  public static synchronized IPluginManager of(IModule module) {

    return MANAGER_MAP.computeIfAbsent(module, PluginManager::new);
  }

  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
