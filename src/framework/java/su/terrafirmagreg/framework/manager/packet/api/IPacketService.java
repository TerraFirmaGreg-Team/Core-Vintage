package su.terrafirmagreg.framework.manager.packet.api;

import su.terrafirmagreg.framework.manager.packet.PacketMap;
import su.terrafirmagreg.framework.module.api.IModule;

public interface IPacketService {

  IModule getModule();

  PacketMap getMap();

}
