package su.terrafirmagreg.modules.core.content.effect;

import su.terrafirmagreg.framework.manager.content.base.effect.spi.BaseEffect;

public class EffectHypothermia extends BaseEffect {

  public EffectHypothermia() {
    super(EffectSettings.of()
      .registryKey("hypothermia")
      .texture("hypothermia")
      .liquidColor(0x5CEBFF)
    );
  }
}
