package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.network.spi.packet.PacketBase;
import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.jetbrains.annotations.NotNull;

/**
 * Update latex status on client, for render purposes
 */
public class SCPacketLatex extends PacketBase<SCPacketLatex> {

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

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    this.pos = BlockPos.fromLong(buf.readLong());
//    this.cutState = buf.readInt();
//    this.fluid = buf.readInt();
//    this.pot = buf.readBoolean();
//    this.base = buf.readBoolean();
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    buf.writeLong(pos.toLong());
//    buf.writeInt(cutState);
//    buf.writeInt(fluid);
//    buf.writeBoolean(pot);
//    buf.writeBoolean(base);
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(context);
    if (player != null) {
      World world = player.getEntityWorld();
      TileUtils.getTile(world, pos, TileLatexExtractor.class)
        .ifPresent(tile -> tile.updateClient(cutState, fluid, pot, base));
    }
    return null;
  }
}
