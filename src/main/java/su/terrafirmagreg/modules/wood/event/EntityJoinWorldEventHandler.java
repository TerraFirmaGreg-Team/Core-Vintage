package su.terrafirmagreg.modules.wood.event;

import su.terrafirmagreg.modules.core.api.capabilities.pull.PullProvider;
import su.terrafirmagreg.modules.wood.objects.entities.ai.EntityWoodAIPullCart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;

public class EntityJoinWorldEventHandler {

    @SubscribeEvent
    public void on(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityLiving entityLiving) {
            entityLiving.tasks.addTask(2, new EntityWoodAIPullCart(entityLiving));
        }
    }

    // TODO
    @SubscribeEvent
    public void on(AttachCapabilitiesEvent<Entity> event) {
        // null check because of a compability issue with MrCrayfish's Furniture Mod and probably others
        // since this event is being fired even when an entity is initialized in the main menu
        if (event.getObject().world != null && !event.getObject().world.isRemote) {
            event.addCapability(new ResourceLocation(MOD_ID), new PullProvider());
        }
    }
}
