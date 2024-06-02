package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.api.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.api.capabilities.damage.HandlerDamageResistance;
import su.terrafirmagreg.api.capabilities.pull.CapabilityPull;
import su.terrafirmagreg.api.capabilities.pull.ProviderPull;
import su.terrafirmagreg.api.capabilities.skill.CapabilitySkill;
import su.terrafirmagreg.api.capabilities.skill.ProviderSkill;
import su.terrafirmagreg.api.capabilities.temperature.CapabilityTemperature;
import su.terrafirmagreg.api.capabilities.temperature.ProviderTemperature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class CapabilitiesEntityHandler {

    @SubscribeEvent
    public void attachItemCapabilities(AttachCapabilitiesEvent<Entity> event) {

        Entity entity = event.getObject();
        if (entity == null) return;

        skill(event, entity);
        temperature(event, entity);
        pull(event, entity);
    }

    public void skill(AttachCapabilitiesEvent<Entity> event, @NotNull Entity entity) {

        if (entity instanceof EntityPlayer player) {
            // Player skills
            if (!CapabilitySkill.has(player)) {
                event.addCapability(CapabilitySkill.KEY, new ProviderSkill(player));
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
            event.addCapability(CapabilityDamageResistance.KEY, HandlerDamageResistance.CUSTOM_ENTITY.get(EntityList.getKey(entity)).get());
        }
    }

}
