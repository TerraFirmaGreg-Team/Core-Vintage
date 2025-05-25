package su.terrafirmagreg.framework.manager.packet;

import su.terrafirmagreg.framework.manager.packet.PacketMap.PacketWrapper;
import su.terrafirmagreg.framework.manager.packet.api.IPacket;
import su.terrafirmagreg.framework.manager.packet.api.IPacketManager;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.fml.relauncher.Side;

import lombok.Getter;

@Getter
public class PacketRegistrar implements IPacketRegistrar {

  private final IModule module;
  private final PacketMap map;

  public PacketRegistrar(IPacketManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();

  }


  public <P extends IPacket> void addPacket(P packet) {

    var packetClass = packet.getClass();
    if (validate(packetClass)) {

      var channel = NetworkThreadedWrapper.of(module.getIdentifier());
      var side = getSide(packetClass);
      var wrapper = PacketWrapper.of(channel, side);

      channel.registerPacket(packetClass);

      this.map.put(packetClass, wrapper);
      IPacketManager.ALL_PACKET_MAP.put(packetClass, wrapper);
    }
  }

  private <P extends IPacket> Side getSide(Class<P> packetClass) {

    return IPacket.Server.class.isAssignableFrom(packetClass) ? Side.SERVER : Side.CLIENT;
  }

  private <P extends IPacket> boolean validate(Class<P> packetClass) {
    if (map.containsKey(packetClass)) {
      throw new IllegalArgumentException("Class '" + packetClass + "' has already been registered!");
    }

    boolean hasServer = IPacket.Server.class.isAssignableFrom(packetClass);
    boolean hasClient = IPacket.Client.class.isAssignableFrom(packetClass);

    if (hasServer && hasClient) {
      PacketManager.LOGGER.error(
        "Could not register packet {}, as it is both a Server and Client executor! Only one allowed. Skipping...",
        packetClass.getName()
      );
      return false;
    }
    if (!hasServer && !hasClient) {
      PacketManager.LOGGER.error(
        "Could not register packet {}, as it does not have an executor! Must have either INetworkPacketServer OR INetworkPacketClient. Skipping...",
        packetClass.getName()
      );
      return false;
    }

    return true;
  }


}
