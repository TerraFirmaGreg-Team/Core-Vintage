package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.spi.effects.PotionBase;

public class PotionHyperthermia extends PotionBase {
	public PotionHyperthermia() {
		super(false, 0xFFC85C);
		formatTexture("hyperthermia");
	}
}
