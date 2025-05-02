package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.api.base.object.effect.spi.BaseEffect;

public class EffectHyperthermia extends BaseEffect {

  public EffectHyperthermia() {

    getSettings()
      .registryKey("hyperthermia")
      .texture("hyperthermia")
      .liquidColor(0xFFC85C);
  }
}
