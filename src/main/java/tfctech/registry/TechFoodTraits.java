package tfctech.registry;

import net.dries007.tfc.api.capability.food.FoodTrait;

public final class TechFoodTraits {
	public static FoodTrait COLD;
	public static FoodTrait FROZEN;

	public static void init() {
		COLD = new FoodTrait("cold", 0.25f);
		FROZEN = new FoodTrait("frozen", 0.1f);
	}
}
