package su.terrafirmagreg.api.data.enums;

import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

public enum EnumHideSize implements IStringSerializable {
  SMALL,
  MEDIUM,
  LARGE;

  private static final EnumHideSize[] VALUES = values();

  @NotNull
  public static EnumHideSize valueOf(int index) {
    return index < 0 || index > VALUES.length ? MEDIUM : VALUES[index];
  }

  @Override
  public String getName() {
    return this.name().toLowerCase();
  }
}
