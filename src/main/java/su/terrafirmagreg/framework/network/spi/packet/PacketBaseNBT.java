package su.terrafirmagreg.framework.network.spi.packet;

import su.terrafirmagreg.framework.network.api.packet.IPacket;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

@Deprecated
public abstract class PacketBaseNBT<REQ extends PacketBaseNBT<REQ>> implements IPacket<REQ> {

  /**
   * Called when the message is received and handled. This is where you process the message.
   *
   * @param context The context for the message.
   * @return A message to send as a response.
   */
  public abstract IMessage handleMessage(MessageContext context);

  /**
   * Read from the nbt tag.
   *
   * @param tag Tag you can read from.
   */
  public abstract void read(NBTTagCompound tag);

  /**
   * Write to the nbt tag.
   *
   * @param tag Tag you can write to.
   */
  public abstract void write(NBTTagCompound tag);

  @Override
  public final void fromBytes(ByteBuf buf) {

    try {

      this.read(ByteBufUtils.readTag(buf));
    } catch (final Exception e) {

      throw new RuntimeException("Error at reading packet " + this.getClass(), e);
    }
  }

  @Override
  public final void toBytes(ByteBuf buf) {

    try {

      final NBTTagCompound tag = new NBTTagCompound();
      this.write(tag);
      ByteBufUtils.writeTag(buf, tag);
    } catch (final Exception e) {

      throw new RuntimeException("Error at writing packet " + this.getClass(), e);
    }
  }
}
