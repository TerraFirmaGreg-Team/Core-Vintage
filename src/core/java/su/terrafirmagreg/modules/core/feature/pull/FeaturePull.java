package su.terrafirmagreg.modules.core.feature.pull;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.core.feature.pull.capability.CapabilityProviderPull;
import su.terrafirmagreg.modules.core.feature.pull.capability.CapabilityPull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeaturePull extends BaseFeature {

  @SubscribeEvent
  public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
    Entity entity = event.getObject();
    World world = entity.world;
    if (world == null) {
      return;
    }

    if (entity instanceof EntityPlayer player) {
      // null check because of a compability issue with MrCrayfish's Furniture Mod and probably others
      // since this event is being fired even when an entity is initialized in the main menu

      //if (event.getObject().world != null && !event.getObject().world.isRemote) {
      if (!CapabilityUtils.has(player, CapabilityPull.CAPABILITY)) {
        event.addCapability(CapabilityPull.KEY, new CapabilityProviderPull());
      }
    }

  }

  @SubscribeEvent
  public static void onPreInit(StateEvent.PreInitialization event) {

    CapabilityPull.register();
  }
}
