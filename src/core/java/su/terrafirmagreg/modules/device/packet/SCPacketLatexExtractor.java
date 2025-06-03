package su.terrafirmagreg.modules.device.packet;

import su.terrafirmagreg.api.base.packet.spi.BasePacketTile;
import su.terrafirmagreg.framework.manager.packet.api.IPacket;
import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

import org.jetbrains.annotations.NotNull;

/**
 * Update latex status on client, for render purposes
 */
public class SCPacketLatexExtractor extends BasePacketTile<TileLatexExtractor> implements IPacket.Client {

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

  @Override
  public Runnable getAction() {
    return () -> tile.updateClient(cutState, fluid, pot, base);
  }
}
