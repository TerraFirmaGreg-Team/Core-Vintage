package su.terrafirmagreg.framework.manager.registry.base.potion.api;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.potion.api.IPotionEntry.PotionSettings;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

import lombok.Getter;

public interface IPotionEntry extends IRegistryEntry<PotionSettings, PotionType> {


  @Getter
  class PotionSettings extends RegistrySettings<PotionSettings> {

    PotionEffect[] effect = new PotionEffect[]{};
    Potion potion;
    int duration;

    protected PotionSettings() {}

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
