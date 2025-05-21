package net.dries007.astikorcarts.handler;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.astikorcarts.AstikorCarts;
import net.dries007.astikorcarts.entity.ai.EntityAIPullCart;

@EventBusSubscriber(modid = AstikorCarts.MODID)
public class CommonEventHandler {


  @SubscribeEvent
  public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
    if (event.getEntity() instanceof EntityLiving entityLiving) {
      entityLiving.tasks.addTask(2, new EntityAIPullCart(entityLiving));
    }
  }
}
