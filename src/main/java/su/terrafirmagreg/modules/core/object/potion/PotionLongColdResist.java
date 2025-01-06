package su.terrafirmagreg.modules.core.object.potion;

import su.terrafirmagreg.api.base.object.potion.spi.BasePotion;
import su.terrafirmagreg.modules.core.init.EffectsCore;

public class PotionLongColdResist extends BasePotion {

  public PotionLongColdResist() {

    getSettings()
      .registryKey("long_cold_resist_type")
      .potion(EffectsCore.COLD_RESIST.get(), 2400);
  }
}
