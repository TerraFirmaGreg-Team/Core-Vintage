package su.terrafirmagreg.modules.core.object.effect;

import su.terrafirmagreg.api.base.object.effect.spi.BaseEffect;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;

public class EffectThirst extends BaseEffect {

  public EffectThirst() {

    getSettings()
      .registryKey("thirst")
      .badEffect()
      .texture("thirst")
      .liquidColor(0x2c86d4);
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
    EntityPlayerMP player = null;
    IFoodStatsTFC foodStatsTFC = null;
    if (entity instanceof EntityPlayerMP entityPlayerMP) {
      player = entityPlayerMP;
      if (player.getFoodStats() instanceof IFoodStatsTFC foodStats) {
        foodStatsTFC = foodStats;
      }
    }

    if (player != null && foodStatsTFC != null) {
      float thirst = foodStatsTFC.getThirst();

      foodStatsTFC.setThirst(thirst - 0.02F * (float) (amplifier + 1));
    }
  }

  @Override
  public boolean isReady(int duration, int amplifier) {
    return true;
  }

}
