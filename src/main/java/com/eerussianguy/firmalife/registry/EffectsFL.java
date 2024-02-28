package com.eerussianguy.firmalife.registry;

import com.eerussianguy.firmalife.player.PotionSwarm;
import net.dries007.tfc.util.Helpers;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static su.terrafirmagreg.api.lib.Constants.MODID_FL;

@Mod.EventBusSubscriber(modid = MODID_FL)
@GameRegistry.ObjectHolder(MODID_FL)
public class EffectsFL {
	public static final Potion SWARM = Helpers.getNull();

	@SubscribeEvent
	public static void registerPotionEffects(RegistryEvent.Register<Potion> event) {
		event.getRegistry().registerAll(
				new PotionSwarm().setRegistryName(MODID_FL, "swarm")
		);
	}
}
