package su.terrafirmagreg.framework.manager.packet;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.packet.api.IPacket;
import su.terrafirmagreg.framework.manager.packet.api.IPacketManager;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketService;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import lombok.Getter;

@Getter
public class PacketManager implements IPacketManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(PacketManager.class);

  private final IModule module;
  private final PacketMap map;

  private final IPacketRegistrar registrar;
  private final IPacketService service;


  private PacketManager(IModule module) {

    this.module = module;
    this.map = PacketMap.of();

    this.registrar = new PacketRegistrar(this);
    this.service = new PacketService(this);

    MinecraftForge.EVENT_BUS.register(this.service);
  }

  public static synchronized IPacketManager of(IModule module) {

    return MANAGER_MAP.computeIfAbsent(module, PacketManager::new);
  }

  public static NetworkThreadedWrapper getChannel(IPacket packet) {
    var packetClass = packet.getClass();
    var wrapper = IPacketManager.ALL_PACKET_MAP.get(packetClass);
    if (wrapper == null) {
      throw new RuntimeException("Trying to send unregistered network packet: " + packetClass.getSimpleName());
    }
    return wrapper.getChannel();
  }


}
