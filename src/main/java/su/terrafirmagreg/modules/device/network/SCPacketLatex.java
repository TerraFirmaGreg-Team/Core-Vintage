package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

import org.jetbrains.annotations.NotNull;

/**
 * Update latex status on client, for render purposes
 */
public class SCPacketLatex implements IMessage, IMessageHandler<SCPacketLatex, IMessage> {

  private BlockPos pos;
  private int cutState = -1;
  private int fluid = 0;
  private boolean pot = false;
  private boolean base = false;

  @SuppressWarnings("unused")
  public SCPacketLatex() {
  }

  public SCPacketLatex(@NotNull TileLatexExtractor tile) {
    this.pos = tile.getPos();
    this.cutState = tile.cutState();
    this.fluid = tile.getFluidAmount();
    this.pot = tile.hasPot();
    this.base = tile.hasBase();
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    this.pos = BlockPos.fromLong(buf.readLong());
    this.cutState = buf.readInt();
    this.fluid = buf.readInt();
    this.pot = buf.readBoolean();
    this.base = buf.readBoolean();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeLong(pos.toLong());
    buf.writeInt(cutState);
    buf.writeInt(fluid);
    buf.writeBoolean(pot);
    buf.writeBoolean(base);
  }

  @Override
  public IMessage onMessage(SCPacketLatex message, MessageContext ctx) {
    EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
    if (player != null) {
      World world = player.getEntityWorld();
      TileUtils.getTile(world, message.pos, TileLatexExtractor.class)
               .ifPresent(tile -> tile.updateClient(message.cutState, message.fluid, message.pot, message.base));
    }
    return null;
  }
}
