package su.terrafirmagreg.data.enums;

import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

public enum EnumFirePitAttachment implements IStringSerializable {
  NONE,
  GRILL,
  COOKING_POT;

  private static final EnumFirePitAttachment[] VALUES = values();

  public static EnumFirePitAttachment valueOf(int i) {
    return i < 0 || i >= VALUES.length ? NONE : VALUES[i];
  }

  @NotNull
  @Override
  public String getName() {
    return name().toLowerCase();
  }
}
