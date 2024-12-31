package su.terrafirmagreg.old.api.base.packet;

import net.minecraft.util.math.BlockPos;

public abstract class BasePacketBlockPos<P extends BasePacketBlockPos<P>> extends BasePacket<P> {

  protected BlockPos blockPos;

  public BasePacketBlockPos() {}

  public BasePacketBlockPos(BlockPos blockPos) {

    this.blockPos = blockPos;
  }

}
