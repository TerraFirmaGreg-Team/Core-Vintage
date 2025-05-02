package su.terrafirmagreg.framework.manager.registry.api;

import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.manager.registry.RegistryMap;

public interface IRegistryManager {


  IModule getModule();

  RegistryMap getMap();

  IRegistryRegistrar getRegistrar();

  IRegistryService getService();


}
