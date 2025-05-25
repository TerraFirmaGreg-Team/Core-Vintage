package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.framework.module.ModuleMap;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IModuleManager {

  Map<String, IModuleManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  String getModId();

  ModuleMap getMap();

  IModuleRegistrar getRegistrar();

  IModuleService getService();


}
