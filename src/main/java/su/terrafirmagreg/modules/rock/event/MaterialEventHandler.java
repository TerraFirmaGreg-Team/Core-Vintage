package su.terrafirmagreg.modules.rock.event;

import su.terrafirmagreg.modules.rock.ModuleRock;
import su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialRockHandler;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import gregtech.api.unification.material.event.MaterialEvent;

public final class MaterialEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void on(MaterialEvent event) {

        MaterialRockHandler.init();
        ModuleRock.LOGGER.info("Registered materials");
    }
}
