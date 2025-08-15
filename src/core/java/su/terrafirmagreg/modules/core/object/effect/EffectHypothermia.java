package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.framework.manager.registry.base.effect.spi.BaseEffect;

public class EffectHypothermia extends BaseEffect {

  public EffectHypothermia() {
    super(EffectSettings.of()
      .registryKey("hypothermia")
      .texture("hypothermia")
      .liquidColor(0x5CEBFF)
    );
  }
}
