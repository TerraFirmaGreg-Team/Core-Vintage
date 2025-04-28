package su.terrafirmagreg.api.base.network.packet.spi;

import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public abstract class NetworkPacketBaseBlockPos extends NetworkPacketBase {

  protected BlockPos blockPos;

  public NetworkPacketBaseBlockPos() {}

  public NetworkPacketBaseBlockPos(BlockPos blockPos) {

    this.blockPos = blockPos;
  }

}
