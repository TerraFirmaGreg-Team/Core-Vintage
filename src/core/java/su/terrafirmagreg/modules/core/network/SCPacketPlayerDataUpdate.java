package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.api.base.network.packet.spi.NetworkPacketBase;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SCPacketPlayerDataUpdate extends NetworkPacketBase implements INetworkPacket.Client {

  public NBTTagCompound tag;

  public SCPacketPlayerDataUpdate() {}

  public SCPacketPlayerDataUpdate(NBTTagCompound tag) {
    this.tag = tag;
  }


  @Override
  public void process(Minecraft minecraft) {
    EntityPlayer player = minecraft.player;
    if (player != null) {
      var capability = CapabilityPlayerData.get(player);
      if (capability != null) {
        capability.deserializeNBT(tag);
      }
    }
  }
}
