package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.loot.ApplyRequiredSkill;
import su.terrafirmagreg.modules.core.object.loot.ApplySimpleSkill;

public final class LootTablesCore {


  public static void onRegister(IRegistryManager registry) {

    registry.loot(new ApplySimpleSkill.Serializer());
    registry.loot(new ApplyRequiredSkill.Serializer());
  }
}
