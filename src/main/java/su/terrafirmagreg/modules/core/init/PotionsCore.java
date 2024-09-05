package su.terrafirmagreg.modules.core.init;

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
    OVERBURDENED = registry.potion("overburdened", new PotionOverburdened());
    THIRST = registry.potion("thirst", new PotionThirst());
    PARASITES = registry.potion("parasites", new PotionParasites());
    SWARM = registry.potion("swarm", new PotionSwarm());
    HYPERTHERMIA = registry.potion("hyperthermia", new PotionHyperthermia());
    HYPOTHERMIA = registry.potion("hypothermia", new PotionHypothermia());
    COLD_RESIST = registry.potion("resist_cold", new PotionResistCold());
    HEAT_RESIST = registry.potion("resist_heat", new PotionResistHeat());

    COLD_RESIST_TYPE = registry.potionType(COLD_RESIST, "cold_resist_type",
        1200);           //TODO cfg duration
    LONG_COLD_RESIST_TYPE = registry.potionType(COLD_RESIST, "long_cold_resist_type",
        2400); //TODO cfg duration
    HEAT_RESIST_TYPE = registry.potionType(HEAT_RESIST, "heat_resist_type",
        1200);           //TODO cfg duration
    LONG_HEAT_RESIST_TYPE = registry.potionType(HEAT_RESIST, "long_heat_resist_type",
        2400); //TODO cfg duration
  }
}
