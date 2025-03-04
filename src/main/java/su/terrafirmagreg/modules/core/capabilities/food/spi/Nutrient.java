package su.terrafirmagreg.modules.core.capabilities.food.spi;

import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

public enum Nutrient {
  GRAIN(TextFormatting.GOLD),
  FRUIT(TextFormatting.GREEN),
  VEGETABLES(TextFormatting.DARK_GREEN),
  PROTEIN(TextFormatting.RED), // This may still be referred to as "meat" in various places. They are the same thing.
  DAIRY(TextFormatting.DARK_PURPLE);

  public static final int TOTAL = values().length;
  private static final Nutrient[] VALUES = values();
  private final TextFormatting color;

  Nutrient(TextFormatting color) {
    this.color = color;
  }

  @Nonnull
  public static Nutrient valueOf(int i) {
    return i >= 0 && i < VALUES.length ? VALUES[i] : GRAIN;
  }

  @Nonnull
  public TextFormatting getColor() {
    return color;
  }
}
