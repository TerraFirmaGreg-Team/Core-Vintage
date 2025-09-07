package su.terrafirmagreg.modules.device.packet;

import su.terrafirmagreg.framework.manager.packet.base.BasePacketTile;
import su.terrafirmagreg.modules.device.content.tile.TileLatexExtractor;

import org.jetbrains.annotations.NotNull;

import lombok.NoArgsConstructor;

/**
 * Update latex status on client, for render purposes
 */
@NoArgsConstructor
public class SCPacketLatexExtractor extends BasePacketTile<TileLatexExtractor> {

  public int cutState = -1;
  public int fluid = 0;
  public boolean pot = false;
  public boolean base = false;
  

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
