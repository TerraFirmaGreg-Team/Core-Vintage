package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.core.content.effect.EffectCaffeine;
import su.terrafirmagreg.modules.core.content.effect.EffectHyperthermia;
import su.terrafirmagreg.modules.core.content.effect.EffectHypothermia;
import su.terrafirmagreg.modules.core.content.effect.EffectOverburdened;
import su.terrafirmagreg.modules.core.content.effect.EffectParasites;
import su.terrafirmagreg.modules.core.content.effect.EffectResistCold;
import su.terrafirmagreg.modules.core.content.effect.EffectResistHeat;
import su.terrafirmagreg.modules.core.content.effect.EffectSwarm;
import su.terrafirmagreg.modules.core.content.effect.EffectThirst;

public final class EffectsCore {

  public static EffectOverburdened OVERBURDENED;
  public static EffectThirst THIRST;
  public static EffectParasites PARASITES;
  public static EffectSwarm SWARM;
  public static EffectHyperthermia HYPERTHERMIA;
  public static EffectHypothermia HYPOTHERMIA;
  public static EffectResistCold COLD_RESIST;
  public static EffectResistHeat HEAT_RESIST;
  public static EffectCaffeine CAFFEINE;

  public static void onRegister(IContentRegistrar registry) {

    OVERBURDENED = registry.addEffect(new EffectOverburdened());
    THIRST = registry.addEffect(new EffectThirst());
    PARASITES = registry.addEffect(new EffectParasites());
    SWARM = registry.addEffect(new EffectSwarm());
    HYPERTHERMIA = registry.addEffect(new EffectHyperthermia());
    HYPOTHERMIA = registry.addEffect(new EffectHypothermia());
    COLD_RESIST = registry.addEffect(new EffectResistCold());
    HEAT_RESIST = registry.addEffect(new EffectResistHeat());
    CAFFEINE = registry.addEffect(new EffectCaffeine());
  }
}
