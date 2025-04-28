package su.terrafirmagreg.modules.core.event.block;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class EventHandlerPortalSpawn {

  @SubscribeEvent
  public static void onNetherPortalActivating(BlockEvent.PortalSpawnEvent event) {
    event.setCanceled(true);
  }
}
