package su.terrafirmagreg.framework.manager.registry.base.potion.api;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.potion.api.IPotionEntry.PotionSettings;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface IPotionEntry extends IRegistryEntry<PotionSettings, PotionType> {


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  class PotionSettings extends RegistrySettings<PotionSettings> {

    protected PotionEffect[] effect = new PotionEffect[]{};
    protected Potion potion;
    protected int duration;

    public static PotionSettings of() {
      return new PotionSettings();
    }

    public PotionSettings potion(Potion potion, int duration) {
      this.potion = potion;
      this.effect = new PotionEffect[]{new PotionEffect(potion, duration)};
      return this.self();
    }
  }
}
