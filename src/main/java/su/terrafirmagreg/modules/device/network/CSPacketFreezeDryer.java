package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import io.netty.buffer.ByteBuf;

public class CSPacketFreezeDryer implements IMessage, IMessageHandler<CSPacketFreezeDryer, IMessage> {

  private int xCoord;
  private int yCoord;
  private int zCoord;
  private int bool;
  private boolean mode;

  public CSPacketFreezeDryer() {
  }

  public CSPacketFreezeDryer(int xCoord, int yCoord, int zCoord, int bool, boolean mode) {
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

  @Override
  public IMessage onMessage(CSPacketFreezeDryer msg, MessageContext ctx) {
    if (ctx.side == Side.SERVER) {
      final var world = ctx.getServerHandler().player.world;
      var tile = TileUtils.getTile(world, new BlockPos(msg.xCoord, msg.yCoord, msg.zCoord), TileFreezeDryer.class);
      if (tile != null) {
        if (msg.bool == 0) {
          if (msg.mode) {
            tile.seal();
          } else {
            tile.unseal();
          }
        }
        if (msg.bool == 1) {
          if (msg.mode) {
            tile.startPump();
          } else {
            tile.stopPump();
          }
        }
      }
    }
    return null;
  }
}
