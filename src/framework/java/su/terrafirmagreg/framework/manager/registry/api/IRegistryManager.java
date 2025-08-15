package su.terrafirmagreg.framework.manager.registry.api;

import su.terrafirmagreg.framework.manager.api.IBaseManager;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IRegistryManager extends IBaseManager<IRegistryEntry<?, ?>> {

  Map<IModuleEntry, IRegistryManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  IRegistryRegistrar getRegistrar();


}
