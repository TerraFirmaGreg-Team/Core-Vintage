package net.dries007.tfc.network;

import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

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

/**
 * Update latex status on client, for render purposes
 */
public class PacketLatexUpdate implements IMessage {

  private BlockPos pos;
  private int cutState = -1;
  private int fluid = 0;
  private boolean pot = false;
  private boolean base = false;

  @SuppressWarnings("unused")
  @Deprecated
  public PacketLatexUpdate() {}

  public PacketLatexUpdate(@Nonnull TileLatexExtractor tile) {
    this.pos = tile.getPos();
    cutState = tile.cutState();
    fluid = tile.getFluidAmount();
    pot = tile.hasPot();
    base = tile.hasBase();
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    pos = BlockPos.fromLong(buf.readLong());
    cutState = buf.readInt();
    fluid = buf.readInt();
    pot = buf.readBoolean();
    base = buf.readBoolean();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeLong(pos.toLong());
    buf.writeInt(cutState);
    buf.writeInt(fluid);
    buf.writeBoolean(pot);
    buf.writeBoolean(base);
  }

  public static class Handler implements IMessageHandler<PacketLatexUpdate, IMessage> {

    @Override
    public IMessage onMessage(PacketLatexUpdate message, MessageContext ctx) {
      EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
      if (player != null) {
        World world = player.getEntityWorld();
        TileLatexExtractor te = Helpers.getTE(world, message.pos, TileLatexExtractor.class);
        if (te != null) {
          te.updateClient(message.cutState, message.fluid, message.pot, message.base);
        }
      }
      return null;
    }
  }
}
