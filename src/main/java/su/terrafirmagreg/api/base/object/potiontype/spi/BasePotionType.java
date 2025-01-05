package su.terrafirmagreg.api.base.object.potiontype.spi;

import su.terrafirmagreg.api.base.object.potiontype.api.IPotionTypeSettings;

import net.minecraft.potion.PotionType;

import lombok.Getter;

@Getter
public class BasePotionType extends PotionType implements IPotionTypeSettings {

  protected final Settings settings;

  public BasePotionType(Settings settings) {
    super(settings.getEffect());
    this.settings = settings;

  }
}
