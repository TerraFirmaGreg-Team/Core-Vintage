package su.terrafirmagreg.framework.manager.network.api;

import su.terrafirmagreg.framework.manager.network.NetworkMap;
import su.terrafirmagreg.framework.module.api.IModule;

public interface INetworkManager {

  IModule getModule();

  NetworkMap getMap();

  INetworkRegistrar getRegistrar();

  INetworkService getService();

}
