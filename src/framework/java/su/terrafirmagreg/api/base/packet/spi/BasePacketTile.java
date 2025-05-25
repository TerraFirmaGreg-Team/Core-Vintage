package su.terrafirmagreg.api.base.packet.spi;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BasePacketTile<T extends TileEntity> extends BasePacket {

  /**
   * The TileEntity.
   */
  public transient T tile;

  /**
   * The message context.
   */
  public transient MessageContext context;

  public BlockPos blockPos;

  /**
   * Basic constructor for a tile entity update message.
   *
   * @param blockPos The position of the tile entity.
   */
  public BasePacketTile(BlockPos blockPos) {
    this.blockPos = blockPos;
  }


  @Override
  public IMessage process(MessageContext context) {
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
