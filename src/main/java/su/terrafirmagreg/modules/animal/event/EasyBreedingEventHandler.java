package su.terrafirmagreg.modules.animal.event;

import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.object.entity.ai.EntityAnimalAIEasyBreeding;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EasyBreedingEventHandler {

  @SubscribeEvent
  public void addAI(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntityLiving() instanceof EntityAnimalBase animal
        && event.getEntityLiving().ticksExisted < 5 && !event.getEntityLiving().isChild()) {
      animal.tasks.addTask(2, new EntityAnimalAIEasyBreeding(animal));
    }
  }
}
