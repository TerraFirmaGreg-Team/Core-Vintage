package su.terrafirmagreg.framework.manager.packet.base;

import su.terrafirmagreg.api.util.NetworkUtils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Base class for packet, which can be send from client to server
 */
public abstract class BasePacketServer extends BasePacket {


  @Override
  public IMessage process(MessageContext context) {
    NetworkUtils.queueTask(context, () -> context.getServerHandler().player, this::process);
    return null;
  }


  public void process(EntityPlayerMP player) {

  }

  // Send To Server

  /**
   * Use it for send packet instance to server
   */
  public void sendToServer() {

    getWrapper().sendToServer(this);
  }


}
