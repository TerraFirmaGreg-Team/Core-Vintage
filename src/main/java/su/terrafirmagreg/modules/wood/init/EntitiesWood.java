package su.terrafirmagreg.modules.wood.init;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.modules.wood.client.render.*;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodAnimalCart;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodBoat;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodPlow;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodSupplyCart;

public final class EntitiesWood {

    public static void onRegister(Registry registry) {
        registry.registerEntity("boat",
                EntityEntryBuilder.create()
                        .entity(EntityWoodBoat.class)
                        .tracker(160, 20, true)
        );

        registry.registerEntity("supply_cart",
                EntityEntryBuilder.create()
                        .entity(EntityWoodSupplyCart.class)
                        .tracker(80, 3, false)
        );

        registry.registerEntity("animal_cart",
                EntityEntryBuilder.create()
                        .entity(EntityWoodAnimalCart.class)
                        .tracker(80, 3, false)
        );

        registry.registerEntity("plow",
                EntityEntryBuilder.create()
                        .entity(EntityWoodPlow.class)
                        .tracker(80, 3, false)
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister() {
        RenderingRegistry.registerEntityRenderingHandler(EntityWoodBoat.class, RenderWoodBoat::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWoodSupplyCart.class, RenderWoodSupplyCart::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWoodAnimalCart.class, RenderWoodAnimalCart::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWoodPlow.class, RenderWoodPlow::new);

    }
}
