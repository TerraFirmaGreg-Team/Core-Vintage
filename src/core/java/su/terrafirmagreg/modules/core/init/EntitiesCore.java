package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.core.content.entity.EntitySeatOn.EntityTypeSeatOn;

public class EntitiesCore {

  public static EntityTypeSeatOn SIT_BLOCK;

  public static void onRegister(IContentRegistrar registrar) {

    SIT_BLOCK = registrar.addEntity(new EntityTypeSeatOn());

  }
}
