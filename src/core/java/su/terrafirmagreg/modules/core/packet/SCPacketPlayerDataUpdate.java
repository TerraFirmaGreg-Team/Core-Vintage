package su.terrafirmagreg.modules.core.packet;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.framework.manager.packet.base.BasePacketClient;
import su.terrafirmagreg.modules.core.feature.playerdata.capability.CapabilityPlayerData;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SCPacketPlayerDataUpdate extends BasePacketClient {

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
