package su.terrafirmagreg.modules.core.object.potion;

import su.terrafirmagreg.api.base.object.potion.spi.BasePotion;
import su.terrafirmagreg.modules.core.init.EffectsCore;

public class PotionHeatResist extends BasePotion {

  public PotionHeatResist() {

    getSettings()
      .registryKey("heat_resist_type")
      .potion(EffectsCore.HEAT_RESIST.get(), 1200);
  }
}
