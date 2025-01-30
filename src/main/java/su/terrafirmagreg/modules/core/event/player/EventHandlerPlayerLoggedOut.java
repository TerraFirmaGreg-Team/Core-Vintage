package su.terrafirmagreg.modules.core.event.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import net.dries007.tfc.objects.container.CapabilityContainerListener;

public class EventHandlerPlayerLoggedOut {

  /**
   * Fired on server only when a player logs out
   *
   * @param event {@link PlayerEvent.PlayerLoggedOutEvent}
   */
  @SubscribeEvent
  public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
    EntityPlayer entityPlayer = event.player;
    if (entityPlayer instanceof EntityPlayerMP playerMP) {
      // Capability sync handler, we can remove it now
      CapabilityContainerListener.removeFrom(playerMP);
    }
  }
}
