package net.sharkbark.cellars.util.handlers;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import io.netty.buffer.ByteBuf;
import net.sharkbark.cellars.blocks.tileentity.TEFreezeDryer;

public class FDPacket implements IMessage {


  private int xCoord;
  private int yCoord;
  private int zCoord;
  private int bool;
  private boolean mode;

  public FDPacket() {}

  public FDPacket(int xCoord, int yCoord, int zCoord, int bool, boolean mode) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.zCoord = zCoord;
    this.bool = bool;
    this.mode = mode;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    xCoord = buf.readInt();
    yCoord = buf.readInt();
    zCoord = buf.readInt();
    bool = buf.readInt();
    mode = buf.readBoolean();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(xCoord);
    buf.writeInt(yCoord);
    buf.writeInt(zCoord);
    buf.writeInt(bool);
    buf.writeBoolean(mode);
  }

  public static class Handler implements IMessageHandler<FDPacket, IMessage> {

    @Override
    public IMessage onMessage(FDPacket msg, MessageContext ctx) {

      if (ctx.side == Side.SERVER) {
        TileEntity tile = ctx.getServerHandler().player.world.getTileEntity(new BlockPos(msg.xCoord, msg.yCoord, msg.zCoord));
        TEFreezeDryer freezeDryer = (TEFreezeDryer) tile;

        if (msg.bool == 0) {
          if (msg.mode) {
            freezeDryer.seal();
          } else {
            freezeDryer.unseal();
          }
        }
        if (msg.bool == 1) {
          if (msg.mode) {
            freezeDryer.startPump();
          } else {
            freezeDryer.stopPump();
          }
        }
      }
      return null;
    }
  }
}
