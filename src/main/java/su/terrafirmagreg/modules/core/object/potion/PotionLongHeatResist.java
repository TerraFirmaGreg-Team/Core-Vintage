package su.terrafirmagreg.modules.core.object.potion;

import su.terrafirmagreg.api.base.object.potion.spi.BasePotion;
import su.terrafirmagreg.modules.core.init.EffectsCore;

public class PotionLongHeatResist extends BasePotion {

  public PotionLongHeatResist() {

    getSettings()
      .registryKey("long_heat_resist_type")
      .potion(EffectsCore.HEAT_RESIST.get(), 2400);
  }
}
