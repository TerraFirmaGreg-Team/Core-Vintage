package su.terrafirmagreg.framework.manager.packet;

import su.terrafirmagreg.framework.manager.packet.api.IPacketManager;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketService;
import su.terrafirmagreg.framework.module.api.IModule;

import lombok.Getter;

@Getter
public class PacketService implements IPacketService {

  private final IModule module;
  private final IPacketRegistrar registrar;
  private final PacketMap map;


  public PacketService(IPacketManager manager) {

    this.module = manager.getModule();
    this.registrar = manager.getRegistrar();
    this.map = manager.getMap();

  }

}
