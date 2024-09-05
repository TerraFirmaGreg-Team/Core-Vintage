package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.base.effects.BasePotion;

public class PotionHypothermia extends BasePotion {

  public PotionHypothermia() {
    super(false, 0x5CEBFF);
    formatTexture("hypothermia");
  }
}
