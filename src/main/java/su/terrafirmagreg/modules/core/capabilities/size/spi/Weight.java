package su.terrafirmagreg.modules.core.capabilities.size.spi;

import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public enum Weight implements IStringSerializable {
  VERY_LIGHT(ConfigCore.MISC.WEIGHT.veryLight),
  LIGHT(ConfigCore.MISC.WEIGHT.light),
  MEDIUM(ConfigCore.MISC.WEIGHT.medium),
  HEAVY(ConfigCore.MISC.WEIGHT.heavy),
  VERY_HEAVY(ConfigCore.MISC.WEIGHT.veryHeavy);

  public final int stackSize;

  Weight(int stackSize) {
    this.stackSize = stackSize;
  }

  public boolean isSmallerThan(Weight other) {
    return this.stackSize > other.stackSize;
  }

  @Override
  public @NotNull String getName() {
    return this.name().toLowerCase();
  }
}
