package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

public class SCPacketTileEntity implements IMessage, IMessageHandler<SCPacketTileEntity, IMessage> {

  private NBTTagCompound tile;
  private BlockPos pos;

  @SuppressWarnings("unused")
  public SCPacketTileEntity() {
  }

  public SCPacketTileEntity(TileEntity tile) {
    this.pos = tile.getPos();
    this.tile = tile.serializeNBT();
  }

  @Override
  public void fromBytes(ByteBuf byteBuf) {
    this.pos = BlockPos.fromLong(byteBuf.readLong());
    this.tile = ByteBufUtils.readTag(byteBuf);
  }

  @Override
  public void toBytes(ByteBuf byteBuf) {
    byteBuf.writeLong(pos.toLong());
    ByteBufUtils.writeTag(byteBuf, tile);
  }

  @Override
  public IMessage onMessage(SCPacketTileEntity message, MessageContext ctx) {
    EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
    if (player != null) {
      World world = player.getEntityWorld();
      var tile = TileUtils.getTile(world, message.pos);
      if (tile != null) {
        tile.readFromNBT(message.tile);
      }
    }
    return null;
  }

}
