package su.terrafirmagreg.modules.core.feature.pull;

import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.feature.pull.capability.CapabilityProviderPull;
import su.terrafirmagreg.modules.core.feature.pull.capability.CapabilityPull;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeaturePull extends FeatureBase {

  @SubscribeEvent
  public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
    Entity entity = event.getObject();
    World world = entity.world;
    if (entity == null && world == null) {
      return;
    }

    // null check because of a compability issue with MrCrayfish's Furniture Mod and probably others
    // since this event is being fired even when an entity is initialized in the main menu
    if (world.isRemote) {
      event.addCapability(CapabilityPull.KEY, new CapabilityProviderPull());
    }
  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    CapabilityPull.register();
  }
}
