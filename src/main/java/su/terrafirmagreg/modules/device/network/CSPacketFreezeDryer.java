package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.framework.network.spi.packet.PacketBaseTile;
import su.terrafirmagreg.modules.device.ModuleDevice;
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
          ModuleDevice.LOGGER.debug("Server Sealed Freeze Dryer");
          this.tile.seal();
        } else {
          ModuleDevice.LOGGER.debug("Server Unsealed Freeze Dryer");
          this.tile.unseal();
        }
      }

      if (this.bool == 1) {
        if (this.mode) {
          ModuleDevice.LOGGER.debug("Server Started Freeze Dryer Pump");
          this.tile.startPump();
        } else {
          ModuleDevice.LOGGER.debug("Server Stopped Freeze Dryer Pump");
          this.tile.stopPump();
        }
      }
    };
  }
}
