package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.damage.HandlerDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.capabilities.player.ProviderPlayer;
import su.terrafirmagreg.modules.core.capabilities.pull.CapabilityPull;
import su.terrafirmagreg.modules.core.capabilities.pull.ProviderPull;
import su.terrafirmagreg.modules.core.capabilities.temperature.CapabilityTemperature;
import su.terrafirmagreg.modules.core.capabilities.temperature.ProviderTemperature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class EventHandlerCapabilitiesEntity {

  @SubscribeEvent
  public void attachItemCapabilities(AttachCapabilitiesEvent<Entity> event) {

    Entity entity = event.getObject();
    if (entity == null) {
      return;
    }

    skill(event, entity);
    temperature(event, entity);
    pull(event, entity);
    //damageResistance(event, entity);
  }

  public void skill(AttachCapabilitiesEvent<Entity> event, @NotNull Entity entity) {

    if (entity instanceof EntityPlayer player) {
      // Player skills
      if (!CapabilityPlayer.has(player)) {
        event.addCapability(CapabilityPlayer.KEY, new ProviderPlayer(player));
      }
    }

  }

  public void temperature(AttachCapabilitiesEvent<Entity> event, @NotNull Entity entity) {

    if (entity instanceof EntityPlayer player) {
      // Each player should have their own instance for each stat, as associated values may vary

      //if (!event.getCapabilities().containsKey(CapabilityTemperature.KEY))
      if (!CapabilityTemperature.has(player)) {
        event.addCapability(CapabilityTemperature.KEY, new ProviderTemperature(player));
      }
    }
  }

  public void pull(AttachCapabilitiesEvent<Entity> event, @NotNull Entity entity) {

    if (entity instanceof EntityPlayer player) {
      // null check because of a compability issue with MrCrayfish's Furniture Mod and probably others
      // since this event is being fired even when an entity is initialized in the main menu

      //if (event.getObject().world != null && !event.getObject().world.isRemote) {
      if (!CapabilityPull.has(player)) {
        event.addCapability(CapabilityPull.KEY, new ProviderPull());
      }
    }

  }

  public void damageResistance(AttachCapabilitiesEvent<Entity> event, @NotNull Entity entity) {

    // Give certain entities damage resistance
    if (!CapabilityDamageResistance.has(entity)) {

      var capabilityProvider = HandlerDamageResistance.getCustom(entity);
      if (capabilityProvider != null) {
        event.addCapability(CapabilityDamageResistance.KEY, capabilityProvider);
      }
    }
  }

}
