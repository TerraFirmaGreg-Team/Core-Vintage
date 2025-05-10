package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.api.base.network.packet.spi.BasePacket;
import su.terrafirmagreg.modules.core.feature.ambiental.capability.CapabilityAmbiental;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class SCPacketAmbiental extends BasePacket implements INetworkPacket.Client {

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
