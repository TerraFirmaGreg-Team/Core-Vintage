package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.object.entity.EntityFallingBlock;
import su.terrafirmagreg.modules.core.object.entity.EntitySeatOn;

import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class EntitiesCore {

  public static void onRegister(RegistryManager registry) {

    registry.entity("sitblock", EntityEntryBuilder.create()
            .entity(EntitySeatOn.class)
            .tracker(160, 20, true)
    );

    registry.entity("falling_block", EntityEntryBuilder.create()
            .entity(EntityFallingBlock.class)
            .tracker(160, 20, true)
    );
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(RegistryManager registry) {

    RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlock.class,
            RenderFallingBlock::new);
  }
}
