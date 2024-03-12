package su.terrafirmagreg.modules.core.data;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.api.loot.ApplyRequiredSkill;
import su.terrafirmagreg.modules.core.api.loot.ApplySimpleSkill;

public class LootTablesCore {

	public static void onRegister(RegistryManager registry) {

		registry.registerLootFunction(new ApplySimpleSkill.Serializer());
		registry.registerLootFunction(new ApplyRequiredSkill.Serializer());
	}
}
