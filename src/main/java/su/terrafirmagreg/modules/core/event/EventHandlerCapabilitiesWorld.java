package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.capabilities.worldtracker.CapabilityWorldTracker;
import su.terrafirmagreg.modules.core.capabilities.worldtracker.ProviderWorldTracker;

import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@SuppressWarnings("unused")
public class EventHandlerCapabilitiesWorld {

    @SubscribeEvent
    public void attachWorldCapabilities(AttachCapabilitiesEvent<World> event) {

        event.addCapability(CapabilityWorldTracker.KEY, new ProviderWorldTracker());
    }

    @SubscribeEvent
    public static void onChunkWatchWatch(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            var tracker = CapabilityWorldTracker.get(event.world);
            if (tracker != null) {
                tracker.tick(event.world);
            }
        }
    }
}
