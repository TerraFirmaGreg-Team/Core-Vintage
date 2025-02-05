package su.terrafirmagreg.modules.core.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.framework.network.spi.packet.PacketBase;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SCPacketPlayerDataUpdate extends PacketBase<SCPacketPlayerDataUpdate> {

  private NBTTagCompound tag;

  public SCPacketPlayerDataUpdate() {}

  public SCPacketPlayerDataUpdate(NBTTagCompound tag) {
    this.tag = tag;
  }


  @Override
  public IMessage handleMessage(MessageContext ctx) {
    TerraFirmaGreg.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
      EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(ctx);
      if (player != null) {
        ICapabilityPlayerData skills = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
        if (skills != null) {
          skills.deserializeNBT(tag);
        }
      }
    });
    return null;
  }
}
