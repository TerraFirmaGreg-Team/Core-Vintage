package su.terrafirmagreg.data.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumLeafState implements IStringSerializable {
  NORMAL,
  FLOWERING,
  FRUIT,
  AUTUMN,
  WINTER;

  private static final EnumLeafState[] VALUES = values();

  public static EnumLeafState valueOf(int index) {
    return index < 0 || index > VALUES.length ? NORMAL : VALUES[index];
  }

  @Override
  public String getName() {
    return this.name().toLowerCase();
  }
}
