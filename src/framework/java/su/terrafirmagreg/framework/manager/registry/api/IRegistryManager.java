package su.terrafirmagreg.framework.manager.registry.api;

import su.terrafirmagreg.framework.manager.registry.RegistryMap;
import su.terrafirmagreg.framework.module.api.IModule;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IRegistryManager {

  Map<IModule, IRegistryManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  IModule getModule();

  RegistryMap getMap();

  IRegistryRegistrar getRegistrar();

  IRegistryService getService();

}
