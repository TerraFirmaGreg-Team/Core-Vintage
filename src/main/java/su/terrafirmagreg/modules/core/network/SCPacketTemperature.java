package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.modules.core.capabilities.temperature.CapabilityTemperature;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SCPacketTemperature extends BasePacket<SCPacketTemperature> {

  public NBTTagCompound tag = new NBTTagCompound();

  public SCPacketTemperature() {
  }

  public SCPacketTemperature(NBTTagCompound tag) {
    this.tag = tag;
  }

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    tag = ByteBufUtils.readTag(buf);
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    ByteBufUtils.writeTag(buf, tag);
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    TerraFirmaGreg.getProxy().getThreadListener(context).addScheduledTask(() -> {
      EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(context);
      if (player != null) {
        var sys = CapabilityTemperature.get(player);
        if (sys != null) {
          sys.deserializeNBT(tag);
        }
      }
    });
    return null;
  }

}
