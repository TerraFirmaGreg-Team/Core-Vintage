package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.TerraFirmaGreg;

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

  private NBTTagCompound tileEntity;
  private BlockPos pos;

  @SuppressWarnings("unused")
  public SCPacketTileEntity() {
  }

  public SCPacketTileEntity(TileEntity tile) {
    pos = tile.getPos();
    tileEntity = tile.serializeNBT();
  }

  @Override
  public void fromBytes(ByteBuf byteBuf) {
    pos = BlockPos.fromLong(byteBuf.readLong());
    tileEntity = ByteBufUtils.readTag(byteBuf);
  }

  @Override
  public void toBytes(ByteBuf byteBuf) {
    byteBuf.writeLong(pos.toLong());
    ByteBufUtils.writeTag(byteBuf, tileEntity);
  }

  @Override
  public IMessage onMessage(SCPacketTileEntity message, MessageContext ctx) {
    EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
    if (player != null) {
      World world = player.getEntityWorld();
      TileEntity tile = world.getTileEntity(message.pos);
      if (tile != null) {
        tile.readFromNBT(message.tileEntity);
      }
    }
    return null;
  }

}
