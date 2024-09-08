package su.terrafirmagreg.modules.core.object.potion;

import su.terrafirmagreg.api.base.effects.BasePotion;
import su.terrafirmagreg.modules.core.init.PotionsCore;

import net.minecraft.entity.EntityLivingBase;

public class PotionResistCold extends BasePotion {

  public PotionResistCold() {
    super(false, 0x8EF1FF);
    formatTexture("resist_cold");
    setBeneficial();
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
    removePotionCoreEffect(entity, PotionsCore.HYPOTHERMIA);
  }
}
