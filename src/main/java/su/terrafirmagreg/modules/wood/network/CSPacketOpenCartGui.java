package su.terrafirmagreg.modules.wood.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.packet.BasePacket;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CSPacketOpenCartGui extends BasePacket<CSPacketOpenCartGui> {

  private int invId;
  private int cartId;

  public CSPacketOpenCartGui() {
  }

  public CSPacketOpenCartGui(int invIdIn, int cartIdIn) {
    invId = invIdIn;
    cartId = cartIdIn;
  }

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    invId = buf.readInt();
//    cartId = buf.readInt();
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    buf.writeInt(invId);
//    buf.writeInt(cartId);
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    EntityPlayerMP player = context.getServerHandler().player;
//    GuiHandler.openGui(player.world, new BlockPos(cartId, 0, 0), player);
    player.openGui(TerraFirmaGreg.getInstance(), invId, player.world, cartId, 0, 0);
    return null;
  }

}
