package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.core.content.loot.ApplyRequiredSkill;
import su.terrafirmagreg.modules.core.content.loot.ApplySimpleSkill;

public final class LootTablesCore {


  public static void onRegister(IContentRegistrar registry) {

    registry.addLootFunction(new ApplySimpleSkill.Serializer());
    registry.addLootFunction(new ApplyRequiredSkill.Serializer());
  }
}
