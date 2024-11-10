package su.terrafirmagreg.api.network;

import net.minecraftforge.fml.relauncher.Side;

public class PacketRegistry implements IPacketRegistry {

  private final ThreadedNetworkWrapper wrapper;

  /**
   * The current discriminator value. This is ticked up automatically as messages are registered.
   */
  private int id = 0;

  public PacketRegistry(ThreadedNetworkWrapper wrapper) {
    this.wrapper = wrapper;
  }

  /**
   * Registers a new packet to the network handler.
   *
   * @param clazz The class of the packet. This class must implement IMessage and IMessageHandler!
   * @param side  The side that receives this packet.
   */
  @Override
  public <P extends IPacket<P>> IPacketRegistry register(Side side, Class<P> clazz) {

    this.wrapper.registerMessage(clazz, clazz, nextId(), side);
    return this;
  }

  private int nextId() {
    return this.id++;
  }

}
