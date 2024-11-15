package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.object.loot.ApplyRequiredSkill;
import su.terrafirmagreg.modules.core.object.loot.ApplySimpleSkill;

public final class LootTablesCore {

  public static void onRegister(RegistryManager registryManager) {

    registryManager.lootFunction(new ApplySimpleSkill.Serializer());
    registryManager.lootFunction(new ApplyRequiredSkill.Serializer());
  }
}
