package su.terrafirmagreg.framework.manager.registry.base.potion.api;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.potion.api.IPotionEntry.Settings;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

import lombok.Getter;

public interface IPotionEntry extends IRegistryEntry<Settings, PotionType> {


  @Getter
  class Settings extends RegistrySettings<Settings> {

    PotionEffect[] effect = new PotionEffect[]{};
    Potion potion;
    int duration;

    protected Settings() {}

    public static Settings of() {
      return new Settings();
    }

    public Settings potion(Potion potion, int duration) {
      this.potion = potion;
      this.effect = new PotionEffect[]{new PotionEffect(potion, duration)};
      return this;
    }
  }
}
