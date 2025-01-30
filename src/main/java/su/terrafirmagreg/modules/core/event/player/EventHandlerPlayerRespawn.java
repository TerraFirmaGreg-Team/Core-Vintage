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

public class EventHandlerPlayerRespawn {

  /**
   * Fired on server only when a player dies and respawns, or travels through dimensions
   *
   * @param event {@link PlayerEvent.PlayerRespawnEvent event}
   */
  @SubscribeEvent
  public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
    EntityPlayer entityPlayer = event.player;
    if (entityPlayer instanceof EntityPlayerMP playerMP) {
      // Capability Sync Handler
      CapabilityContainerListener.addTo(playerMP.inventoryContainer, playerMP);

      // Food Stats
      FoodStatsTFC.replaceFoodStats(playerMP);
      FoodStatsTFC foodStatsTFC = (FoodStatsTFC) playerMP.getFoodStats();
      foodStatsTFC.setFoodLevel(4);
      foodStatsTFC.setThirst(25F);
      playerMP.setHealth(5);

      // Skills / Player data
      ICapabilityPlayerData playerData = CapabilityPlayerData.get(playerMP);
      if (playerData != null) {

        ModuleCore.NETWORK.sendTo(new SCPacketPlayerDataUpdate(playerData.serializeNBT()), playerMP);
      }
    }
  }
}
