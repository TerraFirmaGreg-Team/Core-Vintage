package su.terrafirmagreg.modules.animal.data;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.objects.items.ItemAnimalMisc;


public final class ItemsAnimal {

	public static ItemAnimalMisc BLADDER;
	public static ItemAnimalMisc WOOL;
	public static ItemAnimalMisc WOOL_YARN;
	public static ItemAnimalMisc WOOL_CLOTH;
	public static ItemAnimalMisc SILK_CLOTH;

	public static void onRegister(RegistryManager registry) {

		registry.registerAuto(BLADDER = new ItemAnimalMisc.Builder("product/bladder").build());
		registry.registerAuto(WOOL = new ItemAnimalMisc.Builder("product/wool").build());
		registry.registerAuto(WOOL_YARN = new ItemAnimalMisc.Builder("product/wool_yarn").build());
		registry.registerAuto(WOOL_CLOTH = new ItemAnimalMisc.Builder("product/wool_cloth").build());
		registry.registerAuto(SILK_CLOTH = new ItemAnimalMisc.Builder("product/silk_cloth").build());


	}
}
