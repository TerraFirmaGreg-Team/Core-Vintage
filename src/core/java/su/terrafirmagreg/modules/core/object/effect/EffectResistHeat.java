package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.framework.manager.registry.base.effect.spi.BaseEffect;
import su.terrafirmagreg.modules.core.init.EffectsCore;

import net.minecraft.entity.EntityLivingBase;

public class EffectResistHeat extends BaseEffect {

  public EffectResistHeat() {
    super(EffectSettings.of()
      .beneficial()
      .registryKey("resist_heat")
      .texture("resist_heat")
      .liquidColor(0xFFCD72)
    );
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
    removePotionEffect(entity, EffectsCore.HYPERTHERMIA);
  }
}
