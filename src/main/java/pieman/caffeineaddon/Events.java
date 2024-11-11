package pieman.caffeineaddon;

import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.api.data.Reference.MODID_CAFFEINEADDON;

@Mod.EventBusSubscriber(modid = MODID_CAFFEINEADDON)
public class Events {

  @SubscribeEvent
  public static void onFoodStartEating(LivingEntityUseItemEvent.Start event) {
    //if (AppleCoreAPI.accessor.isFood(event.getItem()))
    //{
    //int hunger = ((ItemFood)(event.getItem())).;

    //if (hunger > 0)
    //{
    //event.setDuration(1);
    //}
    //}
  }
}
