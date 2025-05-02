package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.framework.module.ModuleMap;

public interface IModuleManager {

  String getModId();

  ModuleMap getMap();

  IModuleRegistrar getRegistrar();

  IModuleService getService();


}
