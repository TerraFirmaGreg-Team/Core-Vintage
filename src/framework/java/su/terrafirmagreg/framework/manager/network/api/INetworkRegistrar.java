package su.terrafirmagreg.framework.manager.network.api;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;

public interface INetworkRegistrar {

  /**
   * Registers a new packet to the network handler.
   *
   * @param clazz The class of the packet. This class must implement IMessage and IMessageHandler!
   * @param side  The side that receives this packet.
   */
  <P extends INetworkPacket> void addPacket(Class<P> clazz);
}
