package su.terrafirmagreg.framework.manager.packet.api;

import su.terrafirmagreg.api.library.IBaseEntry;
import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry.Settings;
import su.terrafirmagreg.framework.manager.packet.base.BasePacket;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import lombok.Getter;

public interface IPacketEntry extends IBaseEntry<Settings, BasePacket> {


  /**
   * Checks whether the received values are valid. If {@code false} is returned, the packet will be discarded.
   *
   * @return {@code true} if all received values are valid
   */
  default boolean verify(MessageContext context) {
    return true;
  }

  /**
   * Called when the message is received and handled. This is where you process the message.
   *
   * @param context The context for the message.
   * @return A message to send as a response.
   */
  IMessage process(MessageContext context);


  default NetworkThreadedWrapper getWrapper() {

    return IPacketManager.getChannel(this);
  }


  @Getter
  class Settings extends BaseSettings<Settings> {

    Side side = Side.CLIENT;

    protected Settings() {}

    public static Settings of() {
      return new Settings();
    }


    public Settings serverSide() {
      this.side = Side.SERVER;
      return this;
    }

    public Settings clientSide() {
      this.side = Side.CLIENT;
      return this;
    }

  }


}
