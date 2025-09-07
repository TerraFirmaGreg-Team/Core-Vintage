package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.framework.FrameworkLogger;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IModuleManager {

  Map<String, IModuleManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  String getModId();

  Map<Class<?>, IModuleEntry> getMap();

  IModuleRegistrar getRegistrar();

  void onConstruction();

  FrameworkLogger getLogger();


}
