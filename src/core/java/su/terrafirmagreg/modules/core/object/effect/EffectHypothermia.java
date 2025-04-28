package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.api.base.object.effect.spi.BaseEffect;

public class EffectHypothermia extends BaseEffect {

  public EffectHypothermia() {

    getSettings()
      .registryKey("hypothermia")
      .texture("hypothermia")
      .liquidColor(0x5CEBFF);
  }
}
