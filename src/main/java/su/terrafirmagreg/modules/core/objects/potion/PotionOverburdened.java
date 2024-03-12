package su.terrafirmagreg.modules.core.objects.potion;

import net.minecraft.entity.EntityLivingBase;
import su.terrafirmagreg.api.spi.effects.PotionBase;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
public class PotionOverburdened extends PotionBase {
	public PotionOverburdened() {
		super(true, 0x5A6C91);
		formatTexture("overburdened");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		// Seems player can barely move, but no jumps. Although falling is allowed
		entity.motionX = 0;
		entity.motionZ = 0;
		if (entity.motionY > 0) {
			entity.motionY = 0;
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

}
