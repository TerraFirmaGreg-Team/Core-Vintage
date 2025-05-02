package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public class RegistryManager implements IRegistryManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(RegistryManager.class);
  public static final Map<IModule, IRegistryManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  public static final RegistryMap ALL_REGISTRY_MAP = RegistryMap.of();

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


}
