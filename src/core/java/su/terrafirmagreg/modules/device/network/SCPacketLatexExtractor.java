package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.framework.network.spi.packet.PacketBaseTile;
import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

import org.jetbrains.annotations.NotNull;

/**
 * Update latex status on client, for render purposes
 */
public class SCPacketLatexExtractor extends PacketBaseTile<TileLatexExtractor, SCPacketLatexExtractor> {

  private int cutState = -1;
  private int fluid = 0;
  private boolean pot = false;
  private boolean base = false;


  public SCPacketLatexExtractor() {
  }

  public SCPacketLatexExtractor(@NotNull TileLatexExtractor tile) {
    super(tile.getPos());
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
  public Runnable getAction() {
    return () -> tile.updateClient(cutState, fluid, pot, base);
  }
}
