package su.terrafirmagreg.modules.core.data;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.potion.*;

public final class PotionsCore {

	public static Potion OVERBURDENED;
	public static Potion THIRST;
	public static Potion PARASITES;
	public static Potion SWARM;
	public static Potion HYPERTHERMIA;
	public static Potion HYPOTHERMIA;
	public static Potion COLD_RESIST;
	public static Potion HEAT_RESIST;

	public static PotionType COLD_RESIST_TYPE;
	public static PotionType LONG_COLD_RESIST_TYPE;
	public static PotionType HEAT_RESIST_TYPE;
	public static PotionType LONG_HEAT_RESIST_TYPE;

	public static void onRegister(RegistryManager registry) {
		registry.registerPotion("overburdened", OVERBURDENED = new PotionOverburdened());
		registry.registerPotion("thirst", THIRST = new PotionThirst());
		registry.registerPotion("food_poison", PARASITES = new PotionParasites());
		registry.registerPotion("swarm", SWARM = new PotionSwarm());
		registry.registerPotion("hyperthermia", HYPERTHERMIA = new PotionHyperthermia());
		registry.registerPotion("cool", HYPOTHERMIA = new PotionHypothermia());
		registry.registerPotion("resist_cold", COLD_RESIST = new PotionResistCold());
		registry.registerPotion("resist_heat", HEAT_RESIST = new PotionResistHeat());

		registry.registerPotionType("cold_resist_type", COLD_RESIST_TYPE, COLD_RESIST, 1200);           //TODO cfg duration
		registry.registerPotionType("long_cold_resist_type", LONG_COLD_RESIST_TYPE, COLD_RESIST, 2400); //TODO cfg duration
		registry.registerPotionType("heat_resist_type", HEAT_RESIST_TYPE, HEAT_RESIST, 1200);           //TODO cfg duration
		registry.registerPotionType("long_heat_resist_type", LONG_HEAT_RESIST_TYPE, HEAT_RESIST, 2400); //TODO cfg duration
	}
}
