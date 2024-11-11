package BananaFructa.tfcfarming.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import BananaFructa.tfcfarming.ClientProxy;
import BananaFructa.tfcfarming.TFCFarming;
import io.netty.buffer.ByteBuf;

public class SPacketNutrientDataResponse implements IMessage {

  public boolean accepted;
  public int x;
  public int z;
  public int y = -1;
  public boolean lowInPlanter = false;
  public int n;
  public int p;
  public int k;

  public SPacketNutrientDataResponse() {

  }

  public SPacketNutrientDataResponse(boolean accepted, int n, int p, int k, int x, int z) {
    this.accepted = accepted;
    this.n = n;
    this.p = p;
    this.k = k;
    this.x = x;
    this.z = z;
  }

  public SPacketNutrientDataResponse(boolean accepted, int n, int p, int k, int x, int y, int z, boolean lowInPlanter) {
    this(accepted, n, p, k, x, z);
    this.y = y;
    this.lowInPlanter = lowInPlanter;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    accepted = buf.readBoolean();
    n = buf.readInt();
    p = buf.readInt();
    k = buf.readInt();
    x = buf.readInt();
    z = buf.readInt();
    y = buf.readInt();
    lowInPlanter = buf.readBoolean();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeBoolean(accepted);
    buf.writeInt(n);
    buf.writeInt(p);
    buf.writeInt(k);
    buf.writeInt(x);
    buf.writeInt(z);
    buf.writeInt(y);
    buf.writeBoolean(lowInPlanter);
  }

  public static class Handler implements IMessageHandler<SPacketNutrientDataResponse, IMessage> {

    @Override
    public IMessage onMessage(SPacketNutrientDataResponse message, MessageContext ctx) {
      if (message.accepted) {
        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
          @Override
          public void run() {
            ((ClientProxy) TFCFarming.proxy).setLastResponse(message);
          }
        });
      }
      return null;
    }
  }
}
