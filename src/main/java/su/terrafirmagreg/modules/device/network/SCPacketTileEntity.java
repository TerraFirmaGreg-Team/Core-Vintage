package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SCPacketTileEntity extends BasePacket<SCPacketTileEntity> {

  private NBTTagCompound nbtTagCompound;
  private BlockPos pos;

  @SuppressWarnings("unused")
  public SCPacketTileEntity() {
  }

  public SCPacketTileEntity(TileEntity tile) {
    this.pos = tile.getPos();
    this.nbtTagCompound = tile.serializeNBT();
  }

//  @Override
//  public void fromBytes(ByteBuf byteBuf) {
//    this.pos = BlockPos.fromLong(byteBuf.readLong());
//    this.nbtTagCompound = ByteBufUtils.readTag(byteBuf);
//  }
//
//  @Override
//  public void toBytes(ByteBuf byteBuf) {
//    byteBuf.writeLong(pos.toLong());
//    ByteBufUtils.writeTag(byteBuf, nbtTagCompound);
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(context);
    if (player != null) {
      World world = player.getEntityWorld();
      TileUtils.getTile(world, pos).ifPresent(tile -> tile.readFromNBT(nbtTagCompound));
    }
    return null;
  }

}
