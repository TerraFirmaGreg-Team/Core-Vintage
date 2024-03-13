package su.terrafirmagreg.modules.animal.data;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
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
		registry.registerAuto(WOOL_YARN = new ItemAnimalMisc("product/wool_yarn", Size.VERY_SMALL, Weight.VERY_LIGHT, "string"));
		registry.registerAuto(WOOL_CLOTH = new ItemAnimalMisc("product/wool_cloth", Size.SMALL, Weight.LIGHT, "cloth_high_quality"));
		registry.registerAuto(SILK_CLOTH = new ItemAnimalMisc("product/silk_cloth", Size.SMALL, Weight.LIGHT, "cloth_high_quality"));


	}
}
