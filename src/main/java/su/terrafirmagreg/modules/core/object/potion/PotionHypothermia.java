package su.terrafirmagreg.modules.core.object.potion;

import su.terrafirmagreg.api.base.potion.BasePotion;

public class PotionHypothermia extends BasePotion {

  public PotionHypothermia() {
    super(false, 0x5CEBFF);
    formatTexture("hypothermia");
  }
}
