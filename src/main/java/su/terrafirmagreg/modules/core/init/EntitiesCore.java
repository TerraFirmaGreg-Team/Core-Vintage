package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.core.object.entity.EntitySeatOn;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import java.util.function.Supplier;

public final class EntitiesCore {

  public static Supplier<EntityEntry> SIT_BLOCK;

  public static void onRegister(IRegistryRegistrar registry) {
    SIT_BLOCK = registry.addEntity("sit_block",
      EntityEntryBuilder.create()
        .entity(EntitySeatOn.class)
        .tracker(160, 20, true)
    );

  }
}
