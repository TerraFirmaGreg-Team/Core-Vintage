package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.spi.effects.PotionBase;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;


import net.dries007.tfc.api.capability.food.IFoodStatsTFC;

import org.jetbrains.annotations.NotNull;

public class PotionThirst extends PotionBase {

    public PotionThirst() {
        super(true, 0x2c86d4);
        formatTexture("thirst");
    }

    @Override
    public void performEffect(@NotNull EntityLivingBase entity, int amplifier) {
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
