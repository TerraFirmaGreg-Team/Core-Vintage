package su.terrafirmagreg.modules.core.packet;

import su.terrafirmagreg.api.base.packet.spi.BasePacket;
import su.terrafirmagreg.framework.manager.packet.api.IPacket;
import su.terrafirmagreg.modules.core.feature.ambiental.capability.CapabilityAmbiental;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class SCPacketAmbiental extends BasePacket implements IPacket.Client {

  public NBTTagCompound tag;

  public SCPacketAmbiental() {
    this.tag = new NBTTagCompound();
  }

  public SCPacketAmbiental(NBTTagCompound tag) {
    this.tag = tag;
  }


  @Override
  public void process(Minecraft minecraft) {
    var player = minecraft.player;
    if (player != null) {
      var capability = CapabilityAmbiental.get(player);
      if (capability != null) {
        capability.deserializeNBT(tag);
      }
    }
  }
}
