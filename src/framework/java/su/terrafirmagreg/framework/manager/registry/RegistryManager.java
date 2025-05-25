package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import lombok.Getter;

@Getter
public class RegistryManager implements IRegistryManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(RegistryManager.class);

  private final IModule module;
  private final RegistryMap map;

  private final IRegistryRegistrar registrar;
  private final IRegistryService service;

  private RegistryManager(IModule module) {

    this.module = module;
    this.map = RegistryMap.of();

    this.registrar = new RegistryRegistrar(this);
    this.service = new RegistryService(this);

    MinecraftForge.EVENT_BUS.register(this.service);
  }

  public static synchronized IRegistryManager of(IModule module) {

    return MANAGER_MAP.computeIfAbsent(module, RegistryManager::new);
  }


  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
