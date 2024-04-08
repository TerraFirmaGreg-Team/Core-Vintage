package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.spi.effects.PotionBase;
import su.terrafirmagreg.modules.core.init.PotionsCore;

import net.minecraft.entity.EntityLivingBase;

import org.jetbrains.annotations.NotNull;

public class PotionResistCold extends PotionBase {

    public PotionResistCold() {
        super(false, 0x8EF1FF);
        formatTexture("resist_cold");
        setBeneficial();
    }

    @Override
    public void performEffect(@NotNull EntityLivingBase entity, int amplifier) {
        removePotionCoreEffect(entity, PotionsCore.HYPOTHERMIA);
    }
}
