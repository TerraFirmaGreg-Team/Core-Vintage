package su.terrafirmagreg.modules.core.feature.size.spi;

import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public enum Weight implements IStringSerializable {
  VERY_LIGHT(ConfigCore.FEATURE.SIZE.veryLight),
  LIGHT(ConfigCore.FEATURE.SIZE.light),
  MEDIUM(ConfigCore.FEATURE.SIZE.medium),
  HEAVY(ConfigCore.FEATURE.SIZE.heavy),
  VERY_HEAVY(ConfigCore.FEATURE.SIZE.veryHeavy);

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
