package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.framework.manager.registry.base.effect.spi.BaseEffect;

public class EffectHyperthermia extends BaseEffect {

  public EffectHyperthermia() {

    getSettings()
      .registryKey("hyperthermia")
      .texture("hyperthermia")
      .liquidColor(0xFFC85C);
  }
}
