package su.terrafirmagreg.framework.manager.packet.api;

import su.terrafirmagreg.framework.manager.api.IBaseRegistrar;

public interface IPacketRegistrar extends IBaseRegistrar<IPacketEntry> {

  <P extends IPacketEntry> void addPacket(P entry);
}
