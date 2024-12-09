package su.terrafirmagreg.modules.core.object.potion;

import su.terrafirmagreg.api.base.potion.BasePotion;

import net.minecraft.entity.EntityLivingBase;

public class PotionCaffeine extends BasePotion {

  public PotionCaffeine() {
    super(true, 0x0028120b);
    formatTexture("caffeine");
    //registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "3949b211-3421-404d-8a50-267986cb8ac9", 0.15D, 2);
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
