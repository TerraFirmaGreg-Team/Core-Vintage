package su.terrafirmagreg.data.enums;

import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

public enum EnumPlantPart implements IStringSerializable {
  UPPER,
  MIDDLE,
  LOWER,
  SINGLE;

  public String toString() {
    return this.getName();
  }

  public @NotNull String getName() {
    return name().toLowerCase();
  }
}
