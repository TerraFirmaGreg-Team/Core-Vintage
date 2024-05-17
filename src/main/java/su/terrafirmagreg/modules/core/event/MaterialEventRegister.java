package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.integration.material.MaterialHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import gregtech.api.unification.material.event.MaterialEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class MaterialEventRegister {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerMaterials(MaterialEvent event) {
        MaterialHandler.init();
    }
}
