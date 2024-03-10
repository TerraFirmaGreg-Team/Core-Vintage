package su.terrafirmagreg.modules.animal.data;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.objects.items.ItemAnimalMisc;


public class ItemsAnimal {

	public static ItemAnimalMisc BLADDER;

	public static void onRegister(RegistryManager registry) {

		registry.registerAuto(BLADDER = new ItemAnimalMisc.Builder("bladder").build());


	}
}
