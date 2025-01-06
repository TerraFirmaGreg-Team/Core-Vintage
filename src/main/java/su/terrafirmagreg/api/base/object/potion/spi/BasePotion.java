package su.terrafirmagreg.api.base.object.potion.spi;

import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings;

import net.minecraft.potion.PotionType;

import lombok.Getter;

@Getter
public abstract class BasePotion extends PotionType implements IPotionSettings {

  protected final Settings settings;

  public BasePotion() {
    this(Settings.of());

  }

  public BasePotion(Settings settings) {
    super(settings.getEffect());
    this.settings = settings;

  }
}
