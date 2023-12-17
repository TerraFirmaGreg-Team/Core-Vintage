package pieman.caffeineaddon;

import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
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
