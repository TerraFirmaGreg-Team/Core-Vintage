package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.capabilities.worldtracker.CapabilityWorldTracker;
import su.terrafirmagreg.modules.core.capabilities.worldtracker.ProviderWorldTracker;

import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class EventHandlerCapabilitiesWorld {


  @SubscribeEvent
  public static void attachWorldCapabilities(AttachCapabilitiesEvent<World> event) {

    World world = event.getObject();
    if (world == null) {
      return;
    }

    worldtracker(event, world);
  }

  private static void worldtracker(AttachCapabilitiesEvent<World> event, @NotNull World world) {
    event.addCapability(CapabilityWorldTracker.KEY, new ProviderWorldTracker());
  }
}
