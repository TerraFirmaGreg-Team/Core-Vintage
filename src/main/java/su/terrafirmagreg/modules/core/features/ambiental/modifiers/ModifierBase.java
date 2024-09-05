package su.terrafirmagreg.modules.core.features.ambiental.modifiers;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TranslatorUtil;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.temperature.ProviderTemperature;


import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class ModifierBase implements Comparable<ModifierBase> {

  @Getter
  private final String name;
  @Setter
  private float change = 0f;
  @Setter
  private float potency = 0f;
  @Getter
  private int count = 1;
  @Setter
  @Getter
  private float multiplier = 1f;

  public ModifierBase(String name) {

    this.name = name;
  }

  public ModifierBase(String name, float change, float potency) {
    this.name = name;
    this.change = change;
    this.potency = potency;
  }

  public static Optional<ModifierBase> defined(String name, float change, float potency) {
    return Optional.of(new ModifierBase(name, change, potency));
  }

  public static Optional<ModifierBase> none() {
    return Optional.empty();
  }

  public void addMultiplier(float multiplier) {
    this.setMultiplier(this.getMultiplier() * multiplier);
  }

  public float getChange() {
    return change * multiplier * (count == 1 ? 1f
        : ConfigCore.MISC.TEMPERATURE.diminishedModifierMultiplier);
  }

  public float getPotency() {
    return potency * multiplier * (count == 1 ? 1f
        : ConfigCore.MISC.TEMPERATURE.diminishedModifierMultiplier);
  }

  public void addCount() {
    count++;
  }

  public void absorb(ModifierBase modifier) {
    if (count >= ConfigCore.MISC.TEMPERATURE.modifierCap) {
      return;
    }
    this.count += modifier.count;
    this.change += modifier.change;
    this.potency += modifier.potency;
    this.addMultiplier(modifier.getMultiplier());
  }

  public String getDisplayName() {
    return TranslatorUtil.translate(ModUtils.localize("ambient.modifier." + this.name));
  }

  public void apply(ProviderTemperature temp) {
    // nothing;
  }

  public void cancel(ProviderTemperature temp) {
    // nothing;
  }

  @Override
  public int compareTo(@NotNull ModifierBase modifierBase) {
    return Float.compare(this.change, modifierBase.change);
  }
}
