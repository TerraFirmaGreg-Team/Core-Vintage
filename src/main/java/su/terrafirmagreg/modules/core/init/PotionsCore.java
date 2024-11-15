package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.object.potion.PotionHyperthermia;
import su.terrafirmagreg.modules.core.object.potion.PotionHypothermia;
import su.terrafirmagreg.modules.core.object.potion.PotionOverburdened;
import su.terrafirmagreg.modules.core.object.potion.PotionParasites;
import su.terrafirmagreg.modules.core.object.potion.PotionResistCold;
import su.terrafirmagreg.modules.core.object.potion.PotionResistHeat;
import su.terrafirmagreg.modules.core.object.potion.PotionSwarm;
import su.terrafirmagreg.modules.core.object.potion.PotionThirst;

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

  public static void onRegister(RegistryManager registryManager) {
    OVERBURDENED = registryManager.potion("overburdened", new PotionOverburdened());
    THIRST = registryManager.potion("thirst", new PotionThirst());
    PARASITES = registryManager.potion("parasites", new PotionParasites());
    SWARM = registryManager.potion("swarm", new PotionSwarm());
    HYPERTHERMIA = registryManager.potion("hyperthermia", new PotionHyperthermia());
    HYPOTHERMIA = registryManager.potion("hypothermia", new PotionHypothermia());
    COLD_RESIST = registryManager.potion("resist_cold", new PotionResistCold());
    HEAT_RESIST = registryManager.potion("resist_heat", new PotionResistHeat());

    COLD_RESIST_TYPE = registryManager.potionType(COLD_RESIST, "cold_resist_type", 1200);           //TODO cfg duration
    LONG_COLD_RESIST_TYPE = registryManager.potionType(COLD_RESIST, "long_cold_resist_type", 2400); //TODO cfg duration
    HEAT_RESIST_TYPE = registryManager.potionType(HEAT_RESIST, "heat_resist_type", 1200);           //TODO cfg duration
    LONG_HEAT_RESIST_TYPE = registryManager.potionType(HEAT_RESIST, "long_heat_resist_type", 2400); //TODO cfg duration
  }
}
