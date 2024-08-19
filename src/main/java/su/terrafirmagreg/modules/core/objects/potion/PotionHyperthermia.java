package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.base.effects.BasePotion;

public class PotionHyperthermia extends BasePotion {

    public PotionHyperthermia() {
        super(false, 0xFFC85C);
        formatTexture("hyperthermia");
    }
}
