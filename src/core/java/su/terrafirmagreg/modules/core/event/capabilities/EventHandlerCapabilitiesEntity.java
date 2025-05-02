package su.terrafirmagreg.modules.core.event.capabilities;

import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityHandlerDamageResistance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class EventHandlerCapabilitiesEntity {

  @SubscribeEvent
  public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {

    Entity entity = event.getObject();
    if (entity == null) {
      return;
    }

//    pull(event, entity);
    damageResistance(event, entity);
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

  public static void damageResistance(AttachCapabilitiesEvent<Entity> event, @NotNull Entity entity) {
    ResourceLocation entityType = EntityList.getKey(entity);
    if (entityType == null) {
      return;
    }

    // Give certain entities damage resistance
    if (!CapabilityDamageResistance.has(entity)) {

      var provider = CapabilityHandlerDamageResistance.getCustom(entityType);
      if (provider != null) {
        event.addCapability(CapabilityDamageResistance.KEY, provider);
      }
    }
  }

}
