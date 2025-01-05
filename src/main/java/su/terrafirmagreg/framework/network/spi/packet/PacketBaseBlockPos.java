package su.terrafirmagreg.framework.network.spi.packet;

import net.minecraft.util.math.BlockPos;

public abstract class PacketBaseBlockPos<P extends PacketBaseBlockPos<P>> extends PacketBase<P> {

  protected BlockPos blockPos;

  public PacketBaseBlockPos() {}

  public PacketBaseBlockPos(BlockPos blockPos) {

    this.blockPos = blockPos;
  }

}
