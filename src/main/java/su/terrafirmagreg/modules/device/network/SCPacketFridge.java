package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.tile.TileFridge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

import org.jetbrains.annotations.NotNull;

public class SCPacketFridge implements IMessage, IMessageHandler<SCPacketFridge, IMessage> {

  private BlockPos pos;
  private float efficiency;

  public SCPacketFridge() {
  }

  public SCPacketFridge(@NotNull BlockPos pos, float efficiency) {
    this.pos = pos;
    this.efficiency = efficiency;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    pos = BlockPos.fromLong(buf.readLong());
    efficiency = buf.readFloat();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeLong(pos.toLong());
    buf.writeFloat(efficiency);
  }

  @Override
  public IMessage onMessage(SCPacketFridge message, MessageContext ctx) {
    EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
    if (player != null) {
      World world = player.getEntityWorld();
      var tile = TileUtils.getTile(world, message.pos, TileFridge.class);
      if (tile != null) {
        tile.updateClient(message.efficiency);
      }
    }
    return null;
  }
}
