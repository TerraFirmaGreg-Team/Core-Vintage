package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

public class SCPacketChunkData extends BasePacket<SCPacketChunkData> {

  private NBTTagCompound nbt;
  private int x, z;
  private float regionalTemp, rainfall;

  @SuppressWarnings("unused")
  @Deprecated
  public SCPacketChunkData() {
  }

  public SCPacketChunkData(ChunkPos chunkPos, NBTTagCompound nbt, float regionalTemp, float rainfall) {
    this.x = chunkPos.x;
    this.z = chunkPos.z;
    this.nbt = nbt;
    this.regionalTemp = regionalTemp;
    this.rainfall = rainfall;
  }

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    x = buf.readInt();
//    z = buf.readInt();
//    nbt = ByteBufUtils.readTag(buf);
//    regionalTemp = buf.readFloat();
//    rainfall = buf.readFloat();
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    buf.writeInt(x);
//    buf.writeInt(z);
//    ByteBufUtils.writeTag(buf, nbt);
//    buf.writeFloat(regionalTemp);
//    buf.writeFloat(rainfall);
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    final World world = TerraFirmaGreg.getProxy().getWorld(context);
    if (world != null) {
      TerraFirmaGreg.getProxy().getThreadListener(context).addScheduledTask(() -> {
        // Update client-side chunk data capability
        Chunk chunk = world.getChunk(x, z);
        var data = CapabilityChunkData.get(chunk);
        if (data != null) {
          CapabilityChunkData.CAPABILITY.readNBT(data, null, nbt);
        }

        // Update climate cache
        Climate.update(chunk.getPos(), regionalTemp, rainfall);
      });
    }
    return null;
  }


}
