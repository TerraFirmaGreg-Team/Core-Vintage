package su.terrafirmagreg.api.data.enums;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

/**
 * Enum state for blockstate Used to render the correct texture of this leaf block
 */
public enum EnumFruitLeafState implements IStringSerializable {
  NORMAL, FLOWERING, FRUIT;

  private static final EnumFruitLeafState[] VALUES = values();

  @Nonnull
  public static EnumFruitLeafState valueOf(int index) {
    return index < 0 || index > VALUES.length ? NORMAL : VALUES[index];
  }

  @Override
  public String getName() {
    return this.name().toLowerCase();
  }
}
