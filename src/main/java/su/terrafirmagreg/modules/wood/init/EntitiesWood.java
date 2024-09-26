package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.wood.client.render.RenderWoodAnimalCart;
import su.terrafirmagreg.modules.wood.client.render.RenderWoodBoat;
import su.terrafirmagreg.modules.wood.client.render.RenderWoodPlowCart;
import su.terrafirmagreg.modules.wood.client.render.RenderWoodSupplyCart;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodAnimalCart;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodBoat;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodPlowCart;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodSupplyCart;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class EntitiesWood {

  public static void onRegister(RegistryManager registry) {
    registry.entity("boat",
                    EntityEntryBuilder.create()
                                      .entity(EntityWoodBoat.class)
                                      .tracker(160, 20, true)
    );

    registry.entity("supply_cart",
                    EntityEntryBuilder.create()
                                      .entity(EntityWoodSupplyCart.class)
                                      .tracker(80, 3, false)
    );

    registry.entity("animal_cart",
                    EntityEntryBuilder.create()
                                      .entity(EntityWoodAnimalCart.class)
                                      .tracker(80, 3, false)
    );

    registry.entity("plow_cart",
                    EntityEntryBuilder.create()
                                      .entity(EntityWoodPlowCart.class)
                                      .tracker(80, 3, false)
    );
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(RegistryManager registry) {
    RenderingRegistry.registerEntityRenderingHandler(EntityWoodBoat.class, RenderWoodBoat::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityWoodSupplyCart.class,
                                                     RenderWoodSupplyCart::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityWoodAnimalCart.class,
                                                     RenderWoodAnimalCart::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityWoodPlowCart.class,
                                                     RenderWoodPlowCart::new);

  }
}
