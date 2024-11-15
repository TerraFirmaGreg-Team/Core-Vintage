package su.terrafirmagreg.modules.wood.network;

import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodCart;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SCPacketDrawnUpdate extends BasePacket<SCPacketDrawnUpdate> {

  private int pullingId;
  private int cartId;

  public SCPacketDrawnUpdate() {
  }

  public SCPacketDrawnUpdate(int horseIn, int cartIn) {
    pullingId = horseIn;
    cartId = cartIn;
  }

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    pullingId = buf.readInt();
//    cartId = buf.readInt();
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    buf.writeInt(pullingId);
//    buf.writeInt(cartId);
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    Minecraft.getMinecraft().addScheduledTask(() -> {
      EntityWoodCart cart = (EntityWoodCart) Minecraft.getMinecraft().world.getEntityByID(cartId);
      if (cart != null) {
        if (pullingId < 0) {
          cart.setPulling(null);
        } else {
          cart.setPullingId(pullingId);
        }
      }

    });
    return null;
  }
}
