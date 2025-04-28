package su.terrafirmagreg.framework.manager.network;

import su.terrafirmagreg.framework.manager.network.api.INetworkService;
import su.terrafirmagreg.framework.module.api.IModule;

import lombok.Getter;

@Getter
public class NetworkService implements INetworkService {

  private final IModule module;
  private final NetworkMap map;


  public NetworkService(NetworkManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();

  }


}
