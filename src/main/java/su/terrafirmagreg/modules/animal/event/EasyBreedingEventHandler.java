package su.terrafirmagreg.modules.animal.event;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIEasyBreeding;


public class EasyBreedingEventHandler {
	@SubscribeEvent
	public void addAI(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityAnimalBase animal && event.getEntityLiving().ticksExisted < 5 && !event.getEntityLiving().isChild()) {
			animal.tasks.addTask(2, new EntityAnimalAIEasyBreeding(animal));
		}
	}
}
