package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.integration.material.MaterialHandler;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import gregtech.api.unification.material.event.MaterialEvent;

@SuppressWarnings("unused")
public class MaterialEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void registerMaterials(MaterialEvent event) {
        MaterialHandler.init();
    }
}
