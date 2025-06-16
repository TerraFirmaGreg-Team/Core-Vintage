package su.terrafirmagreg.modules.device.packet;

import su.terrafirmagreg.framework.manager.packet.base.BasePacketTile;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;

import net.minecraft.util.math.BlockPos;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CSPacketFreezeDryer extends BasePacketTile<TileFreezeDryer> {

  public int bool;
  public boolean mode;
  public BlockPos blockPos;
  

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
