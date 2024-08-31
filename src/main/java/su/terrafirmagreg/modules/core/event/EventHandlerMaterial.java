package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.metal.plugin.gregtech.material.MaterialMetalHandler;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import gregtech.api.unification.material.event.MaterialEvent;

@SuppressWarnings("unused")
public class EventHandlerMaterial {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void registerMaterials(MaterialEvent event) {
        MaterialMetalHandler.init();
    }
}
