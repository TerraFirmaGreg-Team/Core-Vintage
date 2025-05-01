package su.terrafirmagreg.api.base.network.packet.spi;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class NetworkPacketBaseTile<T extends TileEntity> extends NetworkPacketBaseBlockPos {

  /**
   * The TileEntity.
   */
  public transient T tile;

  /**
   * The message context.
   */
  public transient MessageContext context;

  /**
   * Blank constructor required for all messages.
   */
  public NetworkPacketBaseTile() {}

  /**
   * Basic constructor for a tile entity update message.
   *
   * @param blockPos The position of the tile entity.
   */
  public NetworkPacketBaseTile(BlockPos blockPos) {
    super(blockPos);
  }


  @Override
  public INetworkPacket process(MessageContext context) {
    this.context = context;
    final World world = context.getServerHandler().player.getEntityWorld();
    TileUtils.getTile(world, blockPos, this.tile.getClass()).ifPresent(tile -> {
      if (world.isBlockLoaded(this.blockPos)) {
        if (world instanceof WorldServer worldServer) {
          worldServer.addScheduledTask(this::getAction);
        }
      }
    });
    return null;
  }


  public abstract Runnable getAction();
}
