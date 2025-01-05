package su.terrafirmagreg.framework.network.api.packet;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IPacket<REQ extends IPacket<REQ>> extends IMessage, IMessageHandler<REQ, IMessage> {

  @Override
  default IMessage onMessage(REQ message, MessageContext context) {

    return message.handleMessage(context);
  }

  /**
   * Called when the message is received and handled. This is where you process the message.
   *
   * @param context The context for the message.
   * @return A message to send as a response.
   */
  IMessage handleMessage(MessageContext context);
}
