package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.api.base.network.packet.spi.NetworkPacketBase;
import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.modules.core.feature.playerdata.capability.CapabilityPlayerData;

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
      CapabilityUtils.getOptional(player, CapabilityPlayerData.CAPABILITY).ifPresent(cap -> {
        cap.deserializeNBT(tag);
      });
    }
  }
}
