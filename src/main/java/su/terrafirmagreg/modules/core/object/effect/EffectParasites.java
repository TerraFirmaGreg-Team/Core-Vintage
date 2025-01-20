package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.api.base.object.effect.spi.BaseEffect;
import su.terrafirmagreg.api.data.DamageSources;

import net.minecraft.entity.EntityLivingBase;

public class EffectParasites extends BaseEffect {

  public EffectParasites() {

    getSettings()
      .registryKey("parasites")
      .badEffect()
      .texture("parasites")
      .liquidColor(0xFFE1B7);
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
