package su.terrafirmagreg.framework.manager.network;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.framework.manager.network.NetworkMap.NetworkWrapper;
import su.terrafirmagreg.framework.manager.network.api.INetworkRegistrar;
import su.terrafirmagreg.framework.manager.network.spi.NetworkThreadedWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import lombok.Getter;

@Getter
public class NetworkRegistrar implements INetworkRegistrar {

  private final IModule module;
  private final NetworkMap map;

  public NetworkRegistrar(NetworkManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();

  }

  public <P extends INetworkPacket> void addPacket(Class<P> packetClass) {

    if (validate(packetClass)) {
      var channel = NetworkThreadedWrapper.of(module.getIdentifier());
      this.map.put(packetClass, NetworkWrapper.of(channel, packetClass.getSimpleName()));
    }
  }

  private <P extends INetworkPacket> boolean validate(Class<P> packetClass) {
    if (NetworkManager.ALL_NETWORK_MAP.containsKey(packetClass)) {
      throw new IllegalArgumentException("Class '" + packetClass + "' has already been registered!");
    }

    boolean hasServer = INetworkPacket.Server.class.isAssignableFrom(packetClass);
    boolean hasClient = INetworkPacket.Client.class.isAssignableFrom(packetClass);

    if (hasServer && hasClient) {
      NetworkManager.LOGGER.error(
        "Could not register packet {}, as it is both a Server and Client executor! Only one allowed. Skipping...",
        packetClass.getName()
      );
      return false;
    }
    if (!hasServer && !hasClient) {
      NetworkManager.LOGGER.error(
        "Could not register packet {}, as it does not have an executor! Must have either INetworkPacketServer OR INetworkPacketClient. Skipping...",
        packetClass.getName()
      );
      return false;
    }

    return true;
  }


}
