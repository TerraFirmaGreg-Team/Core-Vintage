package su.terrafirmagreg.modules.core.feature.ambiental.modifier;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TranslatorUtil;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;

public class ModifierBase implements Comparable<ModifierBase> {

  @Getter
  private final String name;
  @Setter
  private float change;
  @Setter
  private float potency;
  @Getter
  private int count = 1;
  @Setter
  @Getter
  private float multiplier = 1f;

  public ModifierBase(String name) {

    this.name = name;
    this.change = 0f;
    this.potency = 0f;
  }

  public ModifierBase(String name, float change, float potency) {
    this.name = name;
    this.change = change;
    this.potency = potency;
  }

  public float getChange() {
    return change * multiplier * (count == 1 ? 1f : ConfigCore.MISC.AMBIENTAL.diminishedModifierMultiplier);
  }

  public float getPotency() {
    return potency * multiplier * (count == 1 ? 1f : ConfigCore.MISC.AMBIENTAL.diminishedModifierMultiplier);
  }

  public void addCount() {
    count++;
  }

  public void absorb(ModifierBase modifier) {
    if (count >= ConfigCore.MISC.AMBIENTAL.modifierCap) {
      return;
    }
    this.count += modifier.count;
    this.change += modifier.change;
    this.potency += modifier.potency;
    this.addMultiplier(modifier.getMultiplier());
  }

  public void addMultiplier(float multiplier) {
    this.setMultiplier(this.getMultiplier() * multiplier);
  }

  public String getDisplayName() {
    return TranslatorUtil.translate(ModUtils.localize("ambient.modifier", this.name));
  }

  public void apply(CapabilityProviderAmbiental temp) {
    // nothing;
  }

  public void cancel(CapabilityProviderAmbiental temp) {
    // nothing;
  }

  @Override
  public int compareTo(@NotNull ModifierBase modifierBase) {
    return Float.compare(this.change, modifierBase.change);
  }
}
