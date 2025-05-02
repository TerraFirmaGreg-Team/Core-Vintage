package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.api.base.object.effect.spi.BaseEffect;

import net.minecraft.entity.EntityLivingBase;

public class EffectOverburdened extends BaseEffect {

  public EffectOverburdened() {
    super(Settings.of());

    getSettings()
      .registryKey("overburdened")
      .badEffect()
      .texture("overburdened")
      .liquidColor(0x5A6C91);
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
    // Seems player can barely move, but no jumps. Although falling is allowed
    entity.motionX = 0;
    entity.motionZ = 0;
    if (entity.motionY > 0) {
      entity.motionY = 0;
    }
  }

  @Override
  public boolean isReady(int duration, int amplifier) {
    return true;
  }

}
