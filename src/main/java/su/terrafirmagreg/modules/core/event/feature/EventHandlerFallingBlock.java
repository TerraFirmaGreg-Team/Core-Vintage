package su.terrafirmagreg.modules.core.event.feature;

import su.terrafirmagreg.modules.core.capabilities.worldtracker.CapabilityWorldTracker;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@SuppressWarnings("unused")
public class EventHandlerFallingBlock {

  @SubscribeEvent
  public static void onWorldTick(TickEvent.WorldTickEvent event) {
    var phase = event.phase;
    var world = event.world;

    if (phase == TickEvent.Phase.START) {

      var tracker = CapabilityWorldTracker.get(world);
      if (tracker != null) {
        tracker.tick(world);
      }

    }

  }
}
