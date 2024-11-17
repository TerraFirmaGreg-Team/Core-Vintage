package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.capabilities.temperature.CapabilityTemperature;
import su.terrafirmagreg.modules.core.capabilities.temperature.ProviderTemperature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class EventHandlerAmbiental {

  // Ignore this code!
  @SubscribeEvent
  public static void onInteract(EntityInteractSpecific event) {

    Entity entity = event.getTarget();
    EntityPlayer player = event.getEntityPlayer();
    if (entity instanceof EntityVillager villager) {
      long time = villager.world.getWorldTime();
      try {
        MerchantRecipeList list = new MerchantRecipeList();
        for (Field f : EntityVillager.class.getDeclaredFields()) {
          f.setAccessible(true);
          if (f.get(villager) instanceof MerchantRecipeList) {
            list = (MerchantRecipeList) f.get(villager);

            MerchantRecipeList list2 = new MerchantRecipeList();

            for (MerchantRecipe recipe : list) {
              ItemStack itemToBuy = recipe.getItemToBuy().copy();
              ItemStack itemToBuy2 = recipe.getSecondItemToBuy().copy();
              ItemStack itemToSell = recipe.getItemToSell().copy();
              if (itemToSell.hasCapability(CapabilityFood.CAPABILITY, null)) {
                IFood cap = itemToSell.getCapability(CapabilityFood.CAPABILITY, null);
                cap.setCreationDate(time);
              }
              list2.add(new MerchantRecipe(itemToBuy, itemToBuy2, itemToSell));
            }
            f.set(villager, list2);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @SubscribeEvent
  public static void onPlayerDeath(LivingDeathEvent event) {
    if (event.getEntityLiving().world.isRemote) {
      return;
    }
    if (!(event.getEntityLiving() instanceof EntityPlayer player)) {
      return;
    }
    if (CapabilityTemperature.has(player)) {
      var cap = CapabilityTemperature.get(player);
      cap.setTemperature(ProviderTemperature.AVERAGE);
    }
  }

  @SubscribeEvent
  public static void onPlayerSpawn(LivingSpawnEvent event) {
    if (event.getEntityLiving().world.isRemote) {
      return;
    }
    if (!(event.getEntityLiving() instanceof EntityPlayer player)) {
      return;
    }
    player.sendMessage(new TextComponentString("respawned"));
  }

  @SubscribeEvent
  public static void onPlayerUpdate(LivingUpdateEvent event) {
    if (!(event.getEntityLiving() instanceof EntityPlayer player)) {
      return;
    }
    if (player.isCreative()) {
      return;
    }
    CapabilityTemperature.get(player).update();
  }
}
