package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.api.base.object.effect.spi.BaseEffect;

import net.minecraft.entity.EntityLivingBase;

public class EffectCaffeine extends BaseEffect {

  public EffectCaffeine() {

    //registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "3949b211-3421-404d-8a50-267986cb8ac9", 0.15D, 2);

    getSettings()
      .registryKey("caffeine")
      .badEffect()
      .texture("caffeine")
      .liquidColor(0x28120b);
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
  }

  @Override
  public boolean isInstant() {
    return true;
  }

  @Override
  public boolean isReady(int duration, int amplifier) {
    return true;
  }
}
