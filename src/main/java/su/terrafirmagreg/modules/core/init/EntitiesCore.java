package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.entity.EntitySeatOn;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import java.util.function.Supplier;

public final class EntitiesCore {

  public static Supplier<EntityEntry> SIT_BLOCK;

  public static void onRegister(IRegistryManager registry) {
    SIT_BLOCK = registry.entity("sit_block",
      EntityEntryBuilder.create()
        .entity(EntitySeatOn.class)
        .tracker(160, 20, true)
    );

  }
}
