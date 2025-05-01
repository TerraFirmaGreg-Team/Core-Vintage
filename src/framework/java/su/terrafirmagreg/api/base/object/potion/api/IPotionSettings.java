package su.terrafirmagreg.api.base.object.potion.api;

import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

import lombok.Getter;

public interface IPotionSettings extends IBaseSettings<Settings, PotionType> {


  @Getter
  class Settings extends BaseSettings<Settings> {

    PotionEffect[] effect;
    Potion potion;
    int duration;

    protected Settings() {
      this.effect = new PotionEffect[]{};
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
