package su.terrafirmagreg.modules.core.object.potion;

import su.terrafirmagreg.api.base.object.potion.spi.BasePotion;
import su.terrafirmagreg.modules.core.init.EffectsCore;

public class PotionColdResist extends BasePotion {

  public PotionColdResist() {

    getSettings()
      .registryKey("cold_resist_type")
      .potion(EffectsCore.COLD_RESIST.get(), 1200);
  }
}
