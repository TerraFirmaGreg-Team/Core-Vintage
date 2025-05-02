package su.terrafirmagreg.framework.manager.network;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.framework.manager.network.NetworkMap.NetworkWrapper;
import su.terrafirmagreg.framework.manager.network.api.INetworkManager;
import su.terrafirmagreg.framework.manager.network.api.INetworkRegistrar;
import su.terrafirmagreg.framework.manager.network.spi.NetworkThreadedWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.fml.relauncher.Side;

import lombok.Getter;

@Getter
public class NetworkRegistrar implements INetworkRegistrar {

  private final IModule module;
  private final NetworkMap map;

  public NetworkRegistrar(INetworkManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();

  }


  public <P extends INetworkPacket> void addPacket(P packet) {

    var packetClass = packet.getClass();
    if (validate(packetClass)) {

      var channel = NetworkThreadedWrapper.of(module.getIdentifier());
      var side = getSide(packetClass);
      var wrapper = NetworkWrapper.of(channel, side);

      channel.registerPacket(packetClass);

      this.map.put(packetClass, wrapper);
      INetworkManager.ALL_NETWORK_MAP.put(packetClass, wrapper);
    }
  }

  private <P extends INetworkPacket> Side getSide(Class<P> packetClass) {

    return INetworkPacket.Server.class.isAssignableFrom(packetClass) ? Side.SERVER : Side.CLIENT;
  }

  private <P extends INetworkPacket> boolean validate(Class<P> packetClass) {
    if (map.containsKey(packetClass)) {
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
