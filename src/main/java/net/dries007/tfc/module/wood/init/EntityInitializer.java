package net.dries007.tfc.module.wood.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.wood.client.render.RenderWoodBoat;
import net.dries007.tfc.module.wood.common.entity.EntityWoodBoat;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityInitializer {

    public static void onRegister(Registry registry) {

        registry.createEntityEntry("boat", EntityEntryBuilder.create()
                .entity(EntityWoodBoat.class)
                .tracker(80, 1, true)
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister() {
        RenderingRegistry.registerEntityRenderingHandler(EntityWoodBoat.class, RenderWoodBoat::new);
    }
}
