package su.terrafirmagreg.modules.wood.api.types.variant.item;


import su.terrafirmagreg.modules.wood.objects.items.*;

public class WoodItemVariantHandler {

	public static void init() {

		WoodItemVariants.BOAT = new WoodItemVariant
				.Builder("boat")
				.setFactory(ItemWoodBoat::new)
				.build();

		WoodItemVariants.LUMBER = new WoodItemVariant
				.Builder("lumber")
				.setFactory(ItemWoodLumber::new)
				.build();

		WoodItemVariants.WHEEL = new WoodItemVariant
				.Builder("wheel")
				.setFactory(ItemWoodWheel::new)
				.build();

		WoodItemVariants.SUPPLY_CART = new WoodItemVariant
				.Builder("supply_cart")
				.setFactory(ItemWoodSupplyCart::new)
				.build();

		WoodItemVariants.ANIMAL_CART = new WoodItemVariant
				.Builder("animal_cart")
				.setFactory(ItemWoodAnimalCart::new)
				.build();

		WoodItemVariants.PLOW = new WoodItemVariant
				.Builder("plow")
				.setFactory(ItemWoodPlow::new)
				.build();
	}
}
