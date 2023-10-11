package net.dries007.tfc.module.wood.init;

import net.dries007.tfc.module.wood.client.render.RenderWoodBoat;
import net.dries007.tfc.module.wood.objects.entities.EntityWoodBoat;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;

public class EntitiesWood {

    public static void onRegister(Registry registry) {

        registry.createEntityEntry("boat", EntityEntryBuilder.create()
                .entity(EntityWoodBoat.class)
                .tracker(160, 20, true)
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister() {
        RenderingRegistry.registerEntityRenderingHandler(EntityWoodBoat.class, RenderWoodBoat::new);
    }
}
