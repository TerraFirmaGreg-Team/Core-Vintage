package su.terrafirmagreg.api.data.enums;

import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

/**
 * Enum state for blockstate Used to render the correct texture of this leaf block
 */
public enum EnumFruitLeafState implements IStringSerializable {
  NORMAL,
  FLOWERING,
  FRUIT;

  private static final EnumFruitLeafState[] VALUES = values();

  @NotNull
  public static EnumFruitLeafState valueOf(int index) {
    return index < 0 || index > VALUES.length ? NORMAL : VALUES[index];
  }

  @Override
  public String getName() {
    return this.name().toLowerCase();
  }
}
