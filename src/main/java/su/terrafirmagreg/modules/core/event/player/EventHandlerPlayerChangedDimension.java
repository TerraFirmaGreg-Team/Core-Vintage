package su.terrafirmagreg.modules.core.event.player;

import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;
import su.terrafirmagreg.modules.core.network.SCPacketPlayerDataUpdate;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import net.dries007.tfc.objects.container.CapabilityContainerListener;

public class EventHandlerPlayerChangedDimension {

  /*
   * Fired on server, sync capabilities to client whenever player changes dimension.
   */
  @SubscribeEvent
  public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
    EntityPlayer entityPlayer = event.player;
    if (entityPlayer instanceof EntityPlayerMP playerMP) {
      // Capability Sync Handler
      CapabilityContainerListener.addTo(playerMP.inventoryContainer, playerMP);

      // Food Stats
      FoodStatsTFC.replaceFoodStats(playerMP);

      // Skills
      ICapabilityPlayerData playerData = CapabilityPlayerData.get(playerMP);
      if (playerData != null) {
        ModuleCore.NETWORK.sendTo(new SCPacketPlayerDataUpdate(playerData.serializeNBT()), playerMP);
      }
    }
  }
}
