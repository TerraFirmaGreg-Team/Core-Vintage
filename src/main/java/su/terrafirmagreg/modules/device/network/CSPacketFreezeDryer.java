package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.network.spi.packet.PacketBase;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class CSPacketFreezeDryer extends PacketBase<CSPacketFreezeDryer> {

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

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    xCoord = buf.readInt();
//    yCoord = buf.readInt();
//    zCoord = buf.readInt();
//    bool = buf.readInt();
//    mode = buf.readBoolean();
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    buf.writeInt(xCoord);
//    buf.writeInt(yCoord);
//    buf.writeInt(zCoord);
//    buf.writeInt(bool);
//    buf.writeBoolean(mode);
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    if (context.side == Side.SERVER) {
      final var world = context.getServerHandler().player.world;
      TileUtils.getTile(world, new BlockPos(xCoord, yCoord, zCoord), TileFreezeDryer.class).ifPresent(tile -> {
        if (bool == 0) {
          if (mode) {
            tile.seal();
          } else {
            tile.unseal();
          }
        }
        if (bool == 1) {
          if (mode) {
            tile.startPump();
          } else {
            tile.stopPump();
          }
        }
      });
    }
    return null;
  }
}
