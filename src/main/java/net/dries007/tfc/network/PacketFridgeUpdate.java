package net.dries007.tfc.network;

import su.terrafirmagreg.modules.device.object.tile.TileFridge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;

public class PacketFridgeUpdate implements IMessage {

  private BlockPos pos;
  private float efficiency;

  public PacketFridgeUpdate() {}

  public PacketFridgeUpdate(@Nonnull BlockPos pos, float efficiency) {
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

  public static class Handler implements IMessageHandler<PacketFridgeUpdate, IMessage> {

    @Override
    public IMessage onMessage(PacketFridgeUpdate message, MessageContext ctx) {
      EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
      if (player != null) {
        World world = player.getEntityWorld();
        TileFridge te = Helpers.getTE(world, message.pos, TileFridge.class);
        if (te != null) {
          te.updateClient(message.efficiency);
        }
      }
      return null;
    }
  }
}
