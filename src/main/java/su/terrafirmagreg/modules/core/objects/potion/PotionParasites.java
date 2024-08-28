package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.data.DamageSources;
import su.terrafirmagreg.api.base.effects.BasePotion;

import net.minecraft.entity.EntityLivingBase;

public class PotionParasites extends BasePotion {

    public PotionParasites() {
        super(true, 0xFFE1B7);
        formatTexture("parasites");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.attackEntityFrom(DamageSources.FOOD_POISON, 1.0F * (amplifier + 1));
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 40 == 0; // 2 secs = damage
    }

}
