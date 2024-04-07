package su.terrafirmagreg.modules.core.data;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.potion.PotionHyperthermia;
import su.terrafirmagreg.modules.core.objects.potion.PotionHypothermia;
import su.terrafirmagreg.modules.core.objects.potion.PotionOverburdened;
import su.terrafirmagreg.modules.core.objects.potion.PotionParasites;
import su.terrafirmagreg.modules.core.objects.potion.PotionResistCold;
import su.terrafirmagreg.modules.core.objects.potion.PotionResistHeat;
import su.terrafirmagreg.modules.core.objects.potion.PotionSwarm;
import su.terrafirmagreg.modules.core.objects.potion.PotionThirst;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;

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
        OVERBURDENED = registry.registerPotion("overburdened", new PotionOverburdened());
        THIRST = registry.registerPotion("thirst", new PotionThirst());
        PARASITES = registry.registerPotion("parasites", new PotionParasites());
        SWARM = registry.registerPotion("swarm", new PotionSwarm());
        HYPERTHERMIA = registry.registerPotion("hyperthermia", new PotionHyperthermia());
        HYPOTHERMIA = registry.registerPotion("hypothermia", new PotionHypothermia());
        COLD_RESIST = registry.registerPotion("resist_cold", new PotionResistCold());
        HEAT_RESIST = registry.registerPotion("resist_heat", new PotionResistHeat());

        COLD_RESIST_TYPE = registry.registerPotionType("cold_resist_type", COLD_RESIST, 1200);           //TODO cfg duration
        LONG_COLD_RESIST_TYPE = registry.registerPotionType("long_cold_resist_type", COLD_RESIST, 2400); //TODO cfg duration
        HEAT_RESIST_TYPE = registry.registerPotionType("heat_resist_type", HEAT_RESIST, 1200);           //TODO cfg duration
        LONG_HEAT_RESIST_TYPE = registry.registerPotionType("long_heat_resist_type", HEAT_RESIST, 2400); //TODO cfg duration
    }
}
