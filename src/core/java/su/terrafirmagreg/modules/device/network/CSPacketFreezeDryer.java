package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.framework.network.spi.packet.PacketBaseTile;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;

import net.minecraft.util.math.BlockPos;

public class CSPacketFreezeDryer extends PacketBaseTile<TileFreezeDryer, CSPacketFreezeDryer> {

  public int bool;
  public boolean mode;
  public BlockPos blockPos;

  public CSPacketFreezeDryer() {
  }

  public CSPacketFreezeDryer(BlockPos blockPos, int bool, boolean mode) {
    super(blockPos);
    this.bool = bool;
    this.mode = mode;
    this.blockPos = blockPos;
  }

  @Override
  public Runnable getAction() {
    return () -> {
      if (this.bool == 0) {
        if (this.mode) {
          this.tile.seal();
        } else {
          this.tile.unseal();
        }
      }

      if (this.bool == 1) {
        if (this.mode) {
          this.tile.startPump();
        } else {
          this.tile.stopPump();
        }
      }
    };
  }
}
