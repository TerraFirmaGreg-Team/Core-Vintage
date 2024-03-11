package su.terrafirmagreg.modules.core.data;

import net.minecraft.potion.Potion;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.effects.PotionFoodPoison;
import su.terrafirmagreg.modules.core.objects.effects.PotionOverburdened;
import su.terrafirmagreg.modules.core.objects.effects.PotionThirst;

public class EffectsCore {

	public static Potion OVERBURDENED;
	public static Potion THIRST;
	public static Potion FOOD_POISON;

	public static void onRegister(RegistryManager registry) {
		registry.registerPotion(OVERBURDENED = new PotionOverburdened(), "overburdened");
		registry.registerPotion(THIRST = new PotionThirst(), "thirst");
		registry.registerPotion(FOOD_POISON = new PotionFoodPoison(), "food_poison");
	}
}
