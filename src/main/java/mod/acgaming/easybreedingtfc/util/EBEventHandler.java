package mod.acgaming.easybreedingtfc.util;

import mod.acgaming.easybreedingtfc.ai.EBEntityAI;
import net.dries007.tfc.objects.entity.animal.EntityAnimalTFC;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EBEventHandler {
    @SubscribeEvent
    public void addAI(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityAnimalTFC && event.getEntityLiving().ticksExisted < 5 && !event.getEntityLiving().isChild()) {
            EntityAnimalTFC animal = (EntityAnimalTFC) event.getEntityLiving();
            animal.tasks.addTask(2, new EBEntityAI(animal));
        }
    }
}
