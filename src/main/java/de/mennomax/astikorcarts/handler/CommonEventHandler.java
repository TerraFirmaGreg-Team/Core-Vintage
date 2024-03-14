package de.mennomax.astikorcarts.handler;

import de.mennomax.astikorcarts.entity.ai.EntityAIPullCart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import su.terrafirmagreg.modules.core.api.capabilities.pull.PullProvider;

import static su.terrafirmagreg.api.lib.Constants.MODID_ASTIKORCARTS;

@EventBusSubscriber(modid = MODID_ASTIKORCARTS)
public class CommonEventHandler {
	@SubscribeEvent
	public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
		// null check because of a compability issue with MrCrayfish's Furniture Mod and probably others
		// since this event is being fired even when an entity is initialized in the main menu
		if (event.getObject().world != null && !event.getObject().world.isRemote) {
			event.addCapability(new ResourceLocation(MODID_ASTIKORCARTS), new PullProvider());
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityLiving) {
			((EntityLiving) event.getEntity()).tasks.addTask(2, new EntityAIPullCart((EntityLiving) event.getEntity()));
		}
	}
}
