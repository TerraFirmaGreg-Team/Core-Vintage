package su.terrafirmagreg.modules.core.capabilities.size.spi;

import su.terrafirmagreg.modules.core.ConfigCore;


import lombok.Getter;

@Getter
public enum Weight {
  VERY_LIGHT("very_light", ConfigCore.MISC.WEIGHT.veryLight),
  LIGHT("light", ConfigCore.MISC.WEIGHT.light),
  MEDIUM("medium", ConfigCore.MISC.WEIGHT.medium),
  HEAVY("heavy", ConfigCore.MISC.WEIGHT.heavy),
  VERY_HEAVY("very_heavy", ConfigCore.MISC.WEIGHT.veryHeavy);

  public final int stackSize;
  public final String name;

  Weight(String name, int stackSize) {
    this.name = name;
    this.stackSize = stackSize;
  }

  public boolean isSmallerThan(Weight other) {
    return this.stackSize > other.stackSize;
  }
}
