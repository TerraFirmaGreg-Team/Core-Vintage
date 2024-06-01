package su.terrafirmagreg.modules.wood.event;

import su.terrafirmagreg.modules.wood.objects.entities.ai.EntityWoodAIPullCart;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinWorldEventHandler {

    @SubscribeEvent
    public void on(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityLiving entityLiving) {
            entityLiving.tasks.addTask(2, new EntityWoodAIPullCart(entityLiving));
        }
    }
}
