package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.core.object.loot.ApplyRequiredSkill;
import su.terrafirmagreg.modules.core.object.loot.ApplySimpleSkill;

public final class LootTablesCore {


  public static void onRegister(IRegistryRegistrar registry) {

    registry.loot(new ApplySimpleSkill.Serializer());
    registry.loot(new ApplyRequiredSkill.Serializer());
  }
}
