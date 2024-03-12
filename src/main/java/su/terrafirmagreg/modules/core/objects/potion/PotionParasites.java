package su.terrafirmagreg.modules.core.objects.potion;

import net.dries007.tfc.util.DamageSourcesTFC;
import net.minecraft.entity.EntityLivingBase;
import su.terrafirmagreg.api.spi.effects.PotionBase;

import javax.annotation.Nonnull;

public class PotionParasites extends PotionBase {
	public PotionParasites() {
		super(true, 0xFFE1B7);
		formatTexture("parasites");
	}


	@Override
	public void performEffect(@Nonnull EntityLivingBase entity, int amplifier) {
		entity.attackEntityFrom(DamageSourcesTFC.FOOD_POISON, 1.0F * (amplifier + 1));
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 40 == 0; // 2 secs = damage
	}

}
