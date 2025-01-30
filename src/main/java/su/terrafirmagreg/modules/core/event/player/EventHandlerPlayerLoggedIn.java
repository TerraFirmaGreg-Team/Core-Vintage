package su.terrafirmagreg.modules.core.event.player;

import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.capabilities.playerdata.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;
import su.terrafirmagreg.modules.core.network.SCPacketPlayerDataUpdate;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import net.dries007.tfc.objects.container.CapabilityContainerListener;

public class EventHandlerPlayerLoggedIn {

  /**
   * Fired on server only when a player logs in
   *
   * @param event {@link PlayerEvent.PlayerLoggedInEvent}
   */
  @SubscribeEvent
  public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
    EntityPlayer entityPlayer = event.player;
    if (entityPlayer instanceof EntityPlayerMP playerMP) {

      // Capability Sync Handler
      CapabilityContainerListener.addTo(playerMP.inventoryContainer, playerMP);

      // Food Stats
      FoodStatsTFC.replaceFoodStats(playerMP);
      if (playerMP.getFoodStats() instanceof IFoodStatsTFC) {
        // Also need to read the food stats from nbt, as they were not present when the player was loaded
        MinecraftServer server = playerMP.world.getMinecraftServer();
        if (server != null) {
          NBTTagCompound nbt = server.getPlayerList().getPlayerNBT(playerMP);
          // This can be null if the server is unable to read the file
          //noinspection ConstantConditions
          if (nbt != null) {
            playerMP.foodStats.readNBT(nbt);
          }
        }
      }

      // Player Data
      ICapabilityPlayerData playerData = CapabilityPlayerData.get(playerMP);
      if (playerData != null) {

        // Sync
        ModuleCore.NETWORK.sendTo(new SCPacketPlayerDataUpdate(playerData.serializeNBT()), playerMP);
      }
    }
  }
}
