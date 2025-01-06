package su.terrafirmagreg.api.base.object.potion.api;

import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import lombok.Getter;

public interface IPotionSettings extends IBaseSettings<Settings> {


  @Getter
  class Settings extends BaseSettings<Settings> {

    PotionEffect[] effect = new PotionEffect[]{};
    Potion potion;
    int duration;

    protected Settings() {
    }

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
