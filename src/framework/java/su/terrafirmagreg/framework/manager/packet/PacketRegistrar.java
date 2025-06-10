package su.terrafirmagreg.framework.manager.packet;

import su.terrafirmagreg.framework.manager.packet.PacketMap.PacketWrapper;
import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry;
import su.terrafirmagreg.framework.manager.packet.api.IPacketManager;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import lombok.Getter;

@Getter
public class PacketRegistrar implements IPacketRegistrar {

  private final IModule module;
  private final PacketMap map;

  public PacketRegistrar(IPacketManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();

  }


  public <P extends IPacketEntry> void addPacket(P packet) {

    var packetClass = packet.getClass();
    var settings = packet.getSettings();

    if (validate(packetClass)) {

      var channel = NetworkThreadedWrapper.of(module.getIdentifier());
      var wrapper = PacketWrapper.of(channel, packet);

      channel.registerPacket(packetClass);

      this.map.put(packetClass, packet);
      IPacketManager.ALL_PACKET_MAP.put(packetClass, wrapper);
    }
  }

  private <P extends IPacketEntry> boolean validate(Class<P> packetClass) {
    if (map.containsKey(packetClass)) {
      throw new IllegalArgumentException("Class '" + packetClass + "' has already been registered!");
    }

//    boolean hasServer = IPacketEntry.Server.class.isAssignableFrom(packetClass);
//    boolean hasClient = IPacketEntry.Client.class.isAssignableFrom(packetClass);
//
//    if (hasServer && hasClient) {
//      PacketManager.LOGGER.error(
//        "Could not register packet {}, as it is both a Server and Client executor! Only one allowed. Skipping...",
//        packetClass.getName()
//      );
//      return false;
//    }
//    if (!hasServer && !hasClient) {
//      PacketManager.LOGGER.error(
//        "Could not register packet {}, as it does not have an executor! Must have either INetworkPacketServer OR INetworkPacketClient. Skipping...",
//        packetClass.getName()
//      );
//      return false;
//    }

    return true;
  }


}
