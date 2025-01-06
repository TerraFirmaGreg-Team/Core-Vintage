package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.api.base.object.effect.spi.BaseEffect;
import su.terrafirmagreg.modules.core.init.EffectsCore;

import net.minecraft.entity.EntityLivingBase;

public class EffectResistCold extends BaseEffect {

  public EffectResistCold() {

    getSettings()
      .beneficial()
      .registryKey("resist_cold")
      .texture("resist_cold")
      .liquidColor(0x8EF1FF);
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
    removePotionEffect(entity, EffectsCore.HYPOTHERMIA.get());
  }
}
