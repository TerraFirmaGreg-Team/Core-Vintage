package su.terrafirmagreg.framework.manager.packet.api;

public interface IPacketRegistrar {

  /**
   * Registers a new packet to the network handler.
   *
   * @param clazz The class of the packet. This class must implement IMessage and IMessageHandler!
   * @param side  The side that receives this packet.
   */
  <P extends IPacket> void addPacket(P packet);
}
