package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.client.render.RenderSlingMetal;
import su.terrafirmagreg.modules.device.client.render.RenderSlingStone;
import su.terrafirmagreg.modules.device.client.render.RenderSlingUnknownMetal;
import su.terrafirmagreg.modules.device.object.entity.EntitySlingStone;
import su.terrafirmagreg.modules.device.object.entity.EntitySlingStoneMetal;
import su.terrafirmagreg.modules.device.object.entity.EntitySlingStoneMetalLight;
import su.terrafirmagreg.modules.device.object.entity.EntityUnknownProjectile;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class EntitiesDevice {

  public static EntityEntry SLING_STONE;
  public static EntityEntry SLING_STONE_METAL;
  public static EntityEntry SLING_STONE_METAL_LIGHT;
  public static EntityEntry UNKNOWN_PROJECTILE;

  public static void onRegister(RegistryManager registryManager) {
    SLING_STONE = registryManager.entity("slingstone",
                                         EntityEntryBuilder.create()
                                                           .entity(EntitySlingStone.class)
                                                           .tracker(64, 1, true)
    );

    SLING_STONE_METAL = registryManager.entity("slingstonemetal",
                                               EntityEntryBuilder.create()
                                                                 .entity(EntitySlingStoneMetal.class)
                                                                 .tracker(64, 1, true)
    );

    SLING_STONE_METAL_LIGHT = registryManager.entity("slingstonemetallight",
                                                     EntityEntryBuilder.create()
                                                                       .entity(EntitySlingStoneMetalLight.class)
                                                                       .tracker(64, 1, true)
    );

    UNKNOWN_PROJECTILE = registryManager.entity("unknownprojectile",
                                                EntityEntryBuilder.create()
                                                                  .entity(EntityUnknownProjectile.class)
                                                                  .tracker(64, 1, true)
    );
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(RegistryManager registryManager) {
    RenderingRegistry.registerEntityRenderingHandler(EntitySlingStone.class, RenderSlingStone::new);
    RenderingRegistry.registerEntityRenderingHandler(EntitySlingStoneMetal.class,
                                                     RenderSlingMetal::new);
    RenderingRegistry.registerEntityRenderingHandler(EntitySlingStoneMetalLight.class,
                                                     RenderSlingMetal::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityUnknownProjectile.class,
                                                     RenderSlingUnknownMetal::new);

  }
}
