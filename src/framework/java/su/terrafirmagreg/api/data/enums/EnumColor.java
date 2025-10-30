package su.terrafirmagreg.api.data.enums;

import su.terrafirmagreg.api.library.IStringLocalized;
import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.EnumDyeColor;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum EnumColor implements IStringLocalized {
  BLACK(0, EnumDyeColor.BLACK),
  BLUE(1, EnumDyeColor.BLUE),
  GREEN(2, EnumDyeColor.GREEN),
  CYAN(3, EnumDyeColor.CYAN),
  RED(4, EnumDyeColor.RED),
  PURPLE(5, EnumDyeColor.PURPLE),
  ORANGE(6, EnumDyeColor.ORANGE),
  SILVER(7, EnumDyeColor.SILVER),
  GRAY(8, EnumDyeColor.GRAY),
  LIGHT_BLUE(9, EnumDyeColor.LIGHT_BLUE),
  LIME(10, EnumDyeColor.LIME),
  BROWN(11, EnumDyeColor.BROWN),
  PINK(12, EnumDyeColor.PINK),
  MAGENTA(13, EnumDyeColor.MAGENTA),
  YELLOW(14, EnumDyeColor.YELLOW),
  WHITE(15, EnumDyeColor.WHITE),
  COLORLESS(16, EnumDyeColor.WHITE);

  public static final EnumColor[] VALUES = values();

  private final EnumDyeColor dyeColor;
  private final int number;
  private final int rgb;

  EnumColor(int number, EnumDyeColor dyeColor) {
    this.number = number;
    this.dyeColor = dyeColor;

    rgb = getRGB(number);
  }

  public static EnumColor fromDyeColor(EnumDyeColor dyeColor) {
    for (EnumColor color : VALUES) {
      if (color.dyeColor == dyeColor) {
        return color;
      }
    }
    return null;
  }

  private int getRGB(int i) {

    int j = (i >> 3 & 1) * 85;
    int k = (i >> 2 & 1) * 170 + j;
    int l = (i >> 1 & 1) * 170 + j;
    int i1 = (i & 1) * 170 + j;

    if (i == 6) {
      k += 85;
    }

    if (GameUtils.getGameSettings().anaglyph) {
      int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
      int k1 = (k * 30 + l * 70) / 100;
      int l1 = (k * 30 + i1 * 70) / 100;
      k = j1;
      l = k1;
      i1 = l1;
    }

    return (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
  }

  /**
   * Alpha, Red, Green, Blue<br> Example: 0xffffaa00, 4294945280
   */
  public int getARGB() {
    return 0xff << 24 | rgb;
  }

  /**
   * Alpha, Red, Green, Blue<br> Example: 0xffaa00ff, 4289331455
   */
  @SuppressWarnings("unused")
  public int getRGBA() {
    return rgb << 8 | 0xff;
  }

  /**
   * Red, Green, Blue<br> Example: 0xffaa00, 16755200
   */
  @SuppressWarnings("unused")
  public int getRGB() {
    return rgb;
  }

  /**
   * Number in enum table<br> Example: 6
   */
  public int getNumber() {
    return number;
  }

  /**
   * Minecraft text colors<br> Example: &#167;6
   *
   * @return The colors in minecraft text style
   */
  public String getCode() {
    return (char) 167 + Integer.toHexString(number);
  }

  @Override
  public String toString() {
    return this.name().toLowerCase();
  }

  @Override
  public @NotNull String getName() {
    return name().toLowerCase(Locale.ROOT);
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(ModUtils.localize("type"), "color", getName());
  }
}
