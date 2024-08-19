package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.base.effects.BasePotion;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;

public class PotionThirst extends BasePotion {

    public PotionThirst() {
        super(true, 0x2c86d4);
        formatTexture("thirst");
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
