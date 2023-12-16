package com.lumintorious.ambiental;

import com.lumintorious.ambiental.capability.TemperatureCapability;
import com.lumintorious.ambiental.effects.TempEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

import static com.lumintorious.ambiental.TFCAmbiental.MODID;

public class TFCAmbientalEventHandler {

	@SubscribeEvent
	public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MODID)) {
			ConfigManager.sync(MODID, Config.Type.INSTANCE);
			TemperatureCapability.AVERAGE = TFCAmbientalConfig.GENERAL.averageTemperature;
			TemperatureCapability.HOT_THRESHOLD = TFCAmbientalConfig.GENERAL.hotThreshold;
			TemperatureCapability.COOL_THRESHOLD = TFCAmbientalConfig.GENERAL.coolThreshold;
			TemperatureCapability.BURN_THRESHOLD = TFCAmbientalConfig.GENERAL.burnThreshold;
			TemperatureCapability.FREEZE_THRESHOLD = TFCAmbientalConfig.GENERAL.freezeThreshold;
			TemperatureCapability.NANO_QUARK_ARMOR_TEMP = TFCAmbientalConfig.GENERAL.nanoOrQuarkTemp;
			TFCAmbientalGuiRenderer.enableOverlay = TFCAmbientalConfig.CLIENT.enableOverlay;
		}
	}

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if (event.getEntityLiving().world.isRemote) {
			return;
		}
		if (!(event.getEntityLiving() instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		if (player.hasCapability(TemperatureCapability.CAPABILITY, null)) {
			TemperatureCapability cap = (TemperatureCapability) player.getCapability(TemperatureCapability.CAPABILITY, null);
			cap.bodyTemperature = TemperatureCapability.AVERAGE;
		}
	}

	@SubscribeEvent
	public void onPlayerSpawn(LivingSpawnEvent event) {
		if (event.getEntityLiving().world.isRemote) {
			return;
		}
		if (!(event.getEntityLiving() instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		player.sendMessage(new TextComponentString("respawned"));
	}

	@SubscribeEvent
	public void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getObject();

			ResourceLocation loc = new ResourceLocation(MODID, "temperature");

			// Each player should have their own instance for each stat, as associated values may vary
			if (!event.getCapabilities().containsKey(loc))
				event.addCapability(loc, new TemperatureCapability(player));
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
		if (!(entityLiving instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer player = (EntityPlayer) entityLiving;
		if (player.isCreative()) {
			return;
		}

		if (!ArrayUtils.contains(TFCAmbientalConfig.GENERAL.allowedDims, player.dimension)) {
			return;
		}

		TemperatureCapability temp = (TemperatureCapability) player.getCapability(TemperatureCapability.CAPABILITY, null);

		temp.update();
	}

	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event) {
		event.getRegistry().register(TempEffect.WARM);
		event.getRegistry().register(TempEffect.COOL);
	}
}
