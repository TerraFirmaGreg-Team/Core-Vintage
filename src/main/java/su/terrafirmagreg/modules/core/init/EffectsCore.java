package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.effect.EffectCaffeine;
import su.terrafirmagreg.modules.core.object.effect.EffectHyperthermia;
import su.terrafirmagreg.modules.core.object.effect.EffectHypothermia;
import su.terrafirmagreg.modules.core.object.effect.EffectOverburdened;
import su.terrafirmagreg.modules.core.object.effect.EffectParasites;
import su.terrafirmagreg.modules.core.object.effect.EffectResistCold;
import su.terrafirmagreg.modules.core.object.effect.EffectResistHeat;
import su.terrafirmagreg.modules.core.object.effect.EffectSwarm;
import su.terrafirmagreg.modules.core.object.effect.EffectThirst;

import java.util.function.Supplier;

public final class EffectsCore {

  public static Supplier<EffectOverburdened> OVERBURDENED;
  public static Supplier<EffectThirst> THIRST;
  public static Supplier<EffectParasites> PARASITES;
  public static Supplier<EffectSwarm> SWARM;
  public static Supplier<EffectHyperthermia> HYPERTHERMIA;
  public static Supplier<EffectHypothermia> HYPOTHERMIA;
  public static Supplier<EffectResistCold> COLD_RESIST;
  public static Supplier<EffectResistHeat> HEAT_RESIST;
  public static Supplier<EffectCaffeine> CAFFEINE;

  public static void onRegister(IRegistryManager registry) {
    OVERBURDENED = registry.effect(new EffectOverburdened());
    THIRST = registry.effect(new EffectThirst());
    PARASITES = registry.effect(new EffectParasites());
    SWARM = registry.effect(new EffectSwarm());
    HYPERTHERMIA = registry.effect(new EffectHyperthermia());
    HYPOTHERMIA = registry.effect(new EffectHypothermia());
    COLD_RESIST = registry.effect(new EffectResistCold());
    HEAT_RESIST = registry.effect(new EffectResistHeat());
    CAFFEINE = registry.effect(new EffectCaffeine());
  }
}
