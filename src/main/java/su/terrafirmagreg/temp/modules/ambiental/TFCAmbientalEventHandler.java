package su.terrafirmagreg.temp.modules.ambiental;

import su.terrafirmagreg.temp.config.TFGConfig;
import su.terrafirmagreg.temp.modules.ambiental.capability.TemperatureCapability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.commons.lang3.ArrayUtils;

import static su.terrafirmagreg.Tags.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class TFCAmbientalEventHandler {

  @SubscribeEvent
  public void onPlayerDeath(LivingDeathEvent event) {
    if (event.getEntityLiving().world.isRemote) {
      return;
    }
    if (!(event.getEntityLiving() instanceof EntityPlayer player)) {
      return;
    }
    if (player.hasCapability(TemperatureCapability.CAPABILITY, null)) {
      TemperatureCapability cap = player.getCapability(TemperatureCapability.CAPABILITY, null);
      cap.bodyTemperature = TemperatureCapability.AVERAGE;
    }
  }

  @SubscribeEvent
  public void onPlayerSpawn(LivingSpawnEvent event) {
    if (event.getEntityLiving().world.isRemote) {
      return;
    }
    if (!(event.getEntityLiving() instanceof EntityPlayer player)) {
      return;
    }
    player.sendMessage(new TextComponentString("respawned"));
  }

  @SubscribeEvent
  public void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
    if (event.getObject() instanceof EntityPlayer player) {

      ResourceLocation loc = new ResourceLocation(MOD_ID, "temperature");

      // Each player should have their own instance for each stat, as associated values may vary
      if (!event.getCapabilities().containsKey(loc)) {event.addCapability(loc, new TemperatureCapability(player));}
    }
//		if (event.getObject() instanceof EntityPlayer)
//		{
//			EntityPlayer player = (EntityPlayer)event.getObject();
//			TemperatureCapability capability = new TemperatureCapability();
//			capability.setPlayer(player);
//			event.addCapability(TemperatureCapability.KEY, capability);
//		}
  }

  @SubscribeEvent
  public void onPlayerUpdate(LivingUpdateEvent event) {

    EntityLivingBase entityLiving = event.getEntityLiving();
    if (!(entityLiving instanceof EntityPlayer player)) {
      return;
    }

    if (player.isCreative()) {
      return;
    }

    if (!ArrayUtils.contains(TFGConfig.GENERAL.allowedDims, player.dimension)) {
      return;
    }

    TemperatureCapability temp = player.getCapability(TemperatureCapability.CAPABILITY, null);

    temp.update();
  }

}
