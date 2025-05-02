package su.terrafirmagreg.modules.core.feature.falling;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.feature.falling.capability.CapabilityWorldTracker;
import su.terrafirmagreg.modules.core.feature.falling.capability.ProviderWorldTracker;

import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FeatureFalling extends FeatureBase {

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {
    CapabilityWorldTracker.register();
  }


  @SubscribeEvent
  public static void onWorldTick(TickEvent.WorldTickEvent event) {
    var phase = event.phase;
    var world = event.world;

    if (phase == TickEvent.Phase.START) {

      CapabilityUtils.getOptional(world, CapabilityWorldTracker.CAPABILITY).ifPresent(cap -> cap.tick(world));
    }

  }

  @SubscribeEvent
  public static void onAttachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
    World world = event.getObject();
    if (world == null) {
      return;
    }

    // TODO проверить
    if (!CapabilityUtils.has(world, CapabilityWorldTracker.CAPABILITY)) {
      event.addCapability(CapabilityWorldTracker.KEY, new ProviderWorldTracker());
    }

  }
}
