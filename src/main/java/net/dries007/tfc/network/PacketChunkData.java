package net.dries007.tfc.network;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.util.climate.ClimateTFC;

public class PacketChunkData implements IMessage {

  private NBTTagCompound nbt;
  private int x, z;
  private float regionalTemp, rainfall;

  @SuppressWarnings("unused")
  @Deprecated
  public PacketChunkData() {
  }

  public PacketChunkData(ChunkPos chunkPos, NBTTagCompound nbt, float regionalTemp, float rainfall) {
    this.x = chunkPos.x;
    this.z = chunkPos.z;
    this.nbt = nbt;
    this.regionalTemp = regionalTemp;
    this.rainfall = rainfall;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    x = buf.readInt();
    z = buf.readInt();
    nbt = ByteBufUtils.readTag(buf);
    regionalTemp = buf.readFloat();
    rainfall = buf.readFloat();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(x);
    buf.writeInt(z);
    ByteBufUtils.writeTag(buf, nbt);
    buf.writeFloat(regionalTemp);
    buf.writeFloat(rainfall);
  }

  public static class Handler implements IMessageHandler<PacketChunkData, IMessage> {

    @Override
    public IMessage onMessage(PacketChunkData message, MessageContext ctx) {
      final World world = TerraFirmaCraft.getProxy().getWorld(ctx);
      if (world != null) {
        TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
          // Update client-side chunk data capability
          Chunk chunk = world.getChunk(message.x, message.z);
          var data = CapabilityChunkData.get(chunk);
          if (data != null) {
            CapabilityChunkData.CAPABILITY.readNBT(data, null, message.nbt);
          }

          // Update climate cache
          ClimateTFC.update(chunk.getPos(), message.regionalTemp, message.rainfall);
        });
      }
      return null;
    }
  }
}
