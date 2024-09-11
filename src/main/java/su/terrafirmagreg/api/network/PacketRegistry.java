package su.terrafirmagreg.api.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public class PacketRegistry implements IPacketRegistry {

  private final ThreadedNetworkWrapper threadedNetworkWrapper;

  /**
   * The current discriminator value. This is ticked up automatically as messages are registered.
   */
  private int id = 0;

  public PacketRegistry(ThreadedNetworkWrapper threadedNetworkWrapper) {
    this.threadedNetworkWrapper = threadedNetworkWrapper;
  }

  /**
   * Registers a new packet to the network handler.
   *
   * @param clazz The class of the packet. This class must implement IMessage and IMessageHandler!
   * @param side  The side that receives this packet.
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public IPacketRegistry register(Class clazz, Side side) {

    this.threadedNetworkWrapper.registerMessage(clazz, clazz, this.nextId(), side);
    return this;
  }

  @Override
  public <REQ extends IMessage, REPLY extends IMessage> IPacketRegistry register(
          Class<? extends IMessageHandler<REQ, REPLY>> messageHandler,
          Class<REQ> requestMessageType,
          Side side) {

    this.threadedNetworkWrapper.registerMessage(messageHandler, requestMessageType, this.nextId(),
            side);
    return this;
  }

  @Override
  public <REQ extends IMessage, REPLY extends IMessage> IPacketRegistry register(
          IMessageHandler<REQ, REPLY> messageHandler,
          Class<REQ> requestMessageType,
          Side side) {

    this.threadedNetworkWrapper.registerMessage(messageHandler, requestMessageType, this.nextId(),
            side);
    return this;
  }

  private int nextId() {
    return this.id++;
  }

}
