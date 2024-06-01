package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.api.capabilities.skill.CapabilitySkill;
import su.terrafirmagreg.api.capabilities.skill.ProviderSkill;

import net.minecraft.entity.Entity;
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
    }

    public void skill(AttachCapabilitiesEvent<Entity> event, @NotNull Entity entity) {

        if (entity instanceof EntityPlayer player) {
            // Player skills
            if (!CapabilitySkill.has(player)) {
                event.addCapability(CapabilitySkill.KEY, new ProviderSkill(player));
            }
        }

    }

}
