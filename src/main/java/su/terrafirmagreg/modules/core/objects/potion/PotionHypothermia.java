package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.spi.effects.PotionBase;

public class PotionHypothermia extends PotionBase {
	public PotionHypothermia() {
		super(false, 0x5CEBFF);
		formatTexture("hypothermia");
	}
}
