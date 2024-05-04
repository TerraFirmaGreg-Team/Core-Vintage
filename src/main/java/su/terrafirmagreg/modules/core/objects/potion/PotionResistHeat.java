package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.spi.effects.BasePotion;
import su.terrafirmagreg.modules.core.init.PotionsCore;

import net.minecraft.entity.EntityLivingBase;

public class PotionResistHeat extends BasePotion {

    public PotionResistHeat() {
        super(false, 0xFFCD72);
        formatTexture("resist_heat");
        setBeneficial();
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        removePotionCoreEffect(entity, PotionsCore.HYPERTHERMIA);
    }
}
