package su.terrafirmagreg.modules.core.content.effect;

import su.terrafirmagreg.api.data.DamageSources;
import su.terrafirmagreg.framework.manager.content.base.effect.spi.BaseEffect;

import net.minecraft.entity.EntityLivingBase;

public class EffectParasites extends BaseEffect {

  public EffectParasites() {
    super(EffectSettings.of()
      .registryKey("parasites")
      .badEffect()
      .texture("parasites")
      .liquidColor(0xFFE1B7)
    );
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {

    entity.attackEntityFrom(DamageSources.PARASITES, 1.0F * (amplifier + 1));
  }

  @Override
  public boolean isReady(int duration, int amplifier) {
    return duration % 40 == 0; // 2 secs = damage
  }

}
