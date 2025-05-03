package su.terrafirmagreg.modules.core.event.capabilities;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class EventHandlerCapabilitiesEntity {

  @SubscribeEvent
  public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {

    Entity entity = event.getObject();
    if (entity == null) {
      return;
    }

//    pull(event, entity);
  }

//  public static void pull(AttachCapabilitiesEvent<Entity> event, @NotNull Entity entity) {
//
//    if (entity instanceof EntityPlayer player) {
//      // null check because of a compability issue with MrCrayfish's Furniture Mod and probably others
//      // since this event is being fired even when an entity is initialized in the main menu
//
//      //if (event.getObject().world != null && !event.getObject().world.isRemote) {
//      if (!CapabilityPull.has(player)) {
//        event.addCapability(CapabilityPull.KEY, new ProviderPull());
//      }
//    }
//
//  }


}
