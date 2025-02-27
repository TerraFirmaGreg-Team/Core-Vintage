package su.terrafirmagreg.framework.network.spi.packet;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class PacketBaseTile<T extends TileEntity, P extends PacketBase<P>> extends PacketBase<P> {

  public BlockPos blockPos;
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
  public PacketBaseTile() {}

  /**
   * Basic constructor for a tile entity update message.
   *
   * @param blockPos The position of the tile entity.
   */
  public PacketBaseTile(BlockPos blockPos) {
    this.blockPos = blockPos;
  }

  @Override
  @SuppressWarnings("unchecked")
  public IMessage handleMessage(MessageContext context) {
    this.context = context;

    var player = TerraFirmaGreg.getProxy().getPlayer(context);
    if (player != null) {
      final World world = player.getEntityWorld();
      TileUtils.getTile(world, this.blockPos).ifPresent(tile -> {
        this.tile = (T) tile;
        if (world.isBlockLoaded(blockPos)) {
          if (world instanceof WorldServer worldServer) {
            worldServer.addScheduledTask(getAction());
          }
        }
      });
    }
    return null;
  }

  public abstract Runnable getAction();
}
