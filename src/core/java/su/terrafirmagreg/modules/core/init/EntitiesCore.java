package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.core.object.entity.EntitySeatOn.EntityTypeSeatOn;

public final class EntitiesCore {

  public static EntityTypeSeatOn SIT_BLOCK;

  public static void onRegister(IRegistryRegistrar registry) {
    
    SIT_BLOCK = registry.addEntity(new EntityTypeSeatOn());

  }
}
