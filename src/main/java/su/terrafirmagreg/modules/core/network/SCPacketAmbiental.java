package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.framework.network.spi.packet.PacketBase;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityAmbiental;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SCPacketAmbiental extends PacketBase<SCPacketAmbiental> {

  public NBTTagCompound tag;

  public SCPacketAmbiental() {
    this.tag = new NBTTagCompound();
  }

  public SCPacketAmbiental(NBTTagCompound tag) {
    this.tag = tag;
  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    TerraFirmaGreg.getProxy().getThreadListener(context).addScheduledTask(() -> {
      var player = TerraFirmaGreg.getProxy().getPlayer(context);
      if (player != null) {
        var sys = CapabilityAmbiental.get(player);
        if (sys != null) {
          sys.deserializeNBT(tag);
        }
      }
    });
    return null;
  }

}
