package su.terrafirmagreg.framework.network.spi.packet;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class PacketBaseTile<T extends TileEntity> extends PacketBaseBlockPos<PacketBaseTile<T>> {

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
    super(blockPos);
  }

  @Override
  public IMessage handleMessage(MessageContext context) {

    this.context = context;
    final World world = context.getServerHandler().player.getEntityWorld();
    TileUtils.getTile(world, blockPos, this.tile.getClass()).ifPresent(tile -> {
      if (world.isBlockLoaded(this.blockPos)) {
        ((WorldServer) world).addScheduledTask(this::getAction);
      }
    });

    return null;
  }

  public abstract Runnable getAction();
}
