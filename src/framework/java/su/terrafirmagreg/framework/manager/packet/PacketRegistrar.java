package su.terrafirmagreg.framework.manager.packet;

import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry;
import su.terrafirmagreg.framework.manager.packet.api.IPacketManager;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class PacketRegistrar implements IPacketRegistrar {

  private final IModuleEntry module;
  private final Multimap<Class<?>, IPacketEntry> mapEntry;

  public PacketRegistrar(IPacketManager manager) {

    this.module = manager.getModule();
    this.mapEntry = manager.getMapEntry();

  }


  @Override
  public <P extends IPacketEntry> void addPacket(P entry) {

    var wrapper = NetworkThreadedWrapper.of(module.getIdentifier());
    wrapper.registerPacket(entry);

    addEntry(entry);
  }


}
