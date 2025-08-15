package su.terrafirmagreg.modules.core.feature.ambiental;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.core.feature.ambiental.capability.CapabilityAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.capability.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.capability.ICapabilityAmbiental;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureAmbiental extends BaseFeature {

  @SubscribeEvent
  public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
    Entity entity = event.getObject();
    if (entity == null) {
      return;
    }

    if (entity instanceof EntityPlayer player) {
      // Each player should have their own instance for each stat, as associated values may vary

      //if (!event.getCapabilities().containsKey(CapabilityTemperature.KEY))
      if (!CapabilityUtils.has(player, CapabilityAmbiental.CAPABILITY)) {
        event.addCapability(CapabilityAmbiental.KEY, new CapabilityProviderAmbiental(player));
      }

    }
  }

  @SubscribeEvent
  public static void onPlayerDeath(LivingDeathEvent event) {

    var entityLiving = event.getEntityLiving();
    if (entityLiving.world.isRemote) {
      return;
    }
    if (!(entityLiving instanceof EntityPlayer player)) {
      return;
    }
    CapabilityUtils.getOptional(player, CapabilityAmbiental.CAPABILITY).ifPresent(cap -> {
      cap.setTemperature(CapabilityProviderAmbiental.AVERAGE);
    });
  }

  @SubscribeEvent
  public static void onPlayerSpawn(LivingSpawnEvent event) {
    var entityLiving = event.getEntityLiving();
    if (entityLiving.world.isRemote) {
      return;
    }
    if (!(entityLiving instanceof EntityPlayer player)) {
      return;
    }
    player.sendMessage(new TextComponentString("respawned"));
  }

  @SubscribeEvent
  public static void onPlayerUpdate(LivingUpdateEvent event) {
    var entityLiving = event.getEntityLiving();

    if (!(entityLiving instanceof EntityPlayer player)) {
      return;
    }
    if (player.isCreative()) {
      return;
    }
    CapabilityUtils.getOptional(player, CapabilityAmbiental.CAPABILITY).ifPresent(ICapabilityAmbiental::update);
  }

  @SubscribeEvent
  public static void onPreInit(StateEvent.PreInitialization event) {

    CapabilityAmbiental.register();
  }

  @SubscribeEvent
  public static void onPostInit(StateEvent.PostInitialization event) {

    CapabilityAmbiental.Handler.init();
  }
}
