package su.terrafirmagreg.api.base.packet;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class SCBasePacketTile<REQ extends SCBasePacketTile> extends BasePacketBlockPos<REQ> {

  public SCBasePacketTile() {
    // serialization
  }

  public SCBasePacketTile(BlockPos blockPos) {

    super(blockPos);
  }

  @Override
  public IMessage onMessage(REQ message, MessageContext ctx) {

    NetHandlerPlayServer serverHandler = ctx.getServerHandler();
    EntityPlayerMP player = serverHandler.player;

    TileEntity tile = TileUtils.getTile(player.getEntityWorld(), message.blockPos);

    return this.onMessage(message, ctx, tile);
  }

  protected abstract IMessage onMessage(REQ message, MessageContext ctx, TileEntity tile);
}
