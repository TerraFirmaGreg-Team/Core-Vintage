package su.terrafirmagreg.modules.metal.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.metal.client.render.RenderMetalPigvil;
import su.terrafirmagreg.modules.metal.objects.entity.EntityMetalPigvil;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class EntitiesMetal {

    public static void onRegister(RegistryManager registry) {

        registry.entity("pigvil", EntityEntryBuilder.create()
                .entity(EntityMetalPigvil.class)
                .tracker(64, 3, true)
                .egg(0xF1AAAA, 0x555555)
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(RegistryManager registry) {

        RenderingRegistry.registerEntityRenderingHandler(EntityMetalPigvil.class, RenderMetalPigvil::new);
    }
}
