package net.dries007.astikorcarts.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import net.dries007.astikorcarts.AstikorCarts;
import net.dries007.astikorcarts.client.render.RenderCargoCart;
import net.dries007.astikorcarts.client.render.RenderMobCart;
import net.dries007.astikorcarts.client.render.RenderPlowCart;
import net.dries007.astikorcarts.entity.EntityCargoCart;
import net.dries007.astikorcarts.entity.EntityMobCart;
import net.dries007.astikorcarts.entity.EntityPlowCart;

@ObjectHolder(AstikorCarts.MODID)
public class ModEntities {

  public static void registerRenders() {
    RenderingRegistry.registerEntityRenderingHandler(EntityCargoCart.class, RenderCargoCart::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityPlowCart.class, RenderPlowCart::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityMobCart.class, RenderMobCart::new);
  }

  @EventBusSubscriber(modid = AstikorCarts.MODID)
  public static class EntityRegistrationHandler {

    private static int id = 0;

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
      event.getRegistry().registerAll(
        createEntry(EntityCargoCart.class, "cargocart", 80, 3, false),
        createEntry(EntityPlowCart.class, "plowcart", 80, 3, false),
        createEntry(EntityMobCart.class, "mobcart", 80, 3, false)
      );
    }

    private static EntityEntry createEntry(final Class<? extends Entity> entityClass, final String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates) {
      ResourceLocation resourceLocation = new ResourceLocation(AstikorCarts.MODID, name);
      return EntityEntryBuilder.create().entity(entityClass).id(resourceLocation, id++).name(resourceLocation.toString())
        .tracker(trackingRange, updateFrequency, sendVelocityUpdates).build();
    }
  }
}
