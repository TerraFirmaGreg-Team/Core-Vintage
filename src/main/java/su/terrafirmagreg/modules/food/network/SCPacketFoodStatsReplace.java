package su.terrafirmagreg.modules.food.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import net.dries007.tfc.api.capability.food.CapabilityFood;

/**
 * This packet is send to client to signal that it may need to replace the vanilla FoodStats with a TFC version Since all the relevant events where this is
 * listened for are server only {@link CapabilityFood}
 */
public class SCPacketFoodStatsReplace extends BasePacket<SCPacketFoodStatsReplace> {

  public SCPacketFoodStatsReplace() {}

  @Override
  public IMessage handleMessage(MessageContext context) {
    TerraFirmaGreg.getProxy().getThreadListener(context).addScheduledTask(() -> {
      EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(context);
      if (player != null) {
        FoodStatsTFC.replaceFoodStats(player);
      }
    });
    return null;
  }

}
