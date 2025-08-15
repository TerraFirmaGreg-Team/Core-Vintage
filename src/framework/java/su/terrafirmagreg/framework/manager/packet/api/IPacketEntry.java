package su.terrafirmagreg.framework.manager.packet.api;

import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry.PacketSettings;
import su.terrafirmagreg.framework.manager.packet.base.BasePacket;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface IPacketEntry extends IBaseEntry<PacketSettings, BasePacket> {


  /**
   * Checks whether the received values are valid. If {@code false} is returned, the packet will be discarded.
   *
   * @return {@code true} if all received values are valid
   */
  boolean verify(MessageContext context);

  /**
   * Called when the message is received and handled. This is where you process the message.
   *
   * @param context The context for the message.
   * @return A message to send as a response.
   */
  IMessage process(MessageContext context);


  default NetworkThreadedWrapper getWrapper() {

    return NetworkThreadedWrapper.getChannel(asEntry());
  }


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  class PacketSettings extends BaseSettings<PacketSettings> {

    protected Side side = Side.CLIENT;
    protected NetworkThreadedWrapper channel;

    public static PacketSettings of() {
      return new PacketSettings();
    }

    @Deprecated
    public PacketSettings channel(NetworkThreadedWrapper channel) {
      this.channel = channel;
      return this.self();
    }


    @Deprecated
    public PacketSettings serverSide() {
      this.side = Side.SERVER;
      return this.self();
    }

    @Deprecated
    public PacketSettings clientSide() {
      this.side = Side.CLIENT;
      return this.self();
    }

  }


}
