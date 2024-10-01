package su.terrafirmagreg.data.enums;

import su.terrafirmagreg.data.lib.MCColor;

import lombok.Getter;

import static su.terrafirmagreg.data.lib.MCColor.DYE_BLACK;
import static su.terrafirmagreg.data.lib.MCColor.DYE_BLUE;
import static su.terrafirmagreg.data.lib.MCColor.DYE_BROWN;
import static su.terrafirmagreg.data.lib.MCColor.DYE_CYAN;
import static su.terrafirmagreg.data.lib.MCColor.DYE_GRAY;
import static su.terrafirmagreg.data.lib.MCColor.DYE_GREEN;
import static su.terrafirmagreg.data.lib.MCColor.DYE_LIGHT_BLUE;
import static su.terrafirmagreg.data.lib.MCColor.DYE_LIGHT_GRAY;
import static su.terrafirmagreg.data.lib.MCColor.DYE_LIME;
import static su.terrafirmagreg.data.lib.MCColor.DYE_MAGENTA;
import static su.terrafirmagreg.data.lib.MCColor.DYE_ORANGE;
import static su.terrafirmagreg.data.lib.MCColor.DYE_PINK;
import static su.terrafirmagreg.data.lib.MCColor.DYE_PURPLE;
import static su.terrafirmagreg.data.lib.MCColor.DYE_RED;
import static su.terrafirmagreg.data.lib.MCColor.DYE_WHITE;
import static su.terrafirmagreg.data.lib.MCColor.DYE_YELLOW;

/**
 * Contains an enum of all the dye colors as RGB.
 */
@Getter
@SuppressWarnings("unused")
public enum VanillaColors {

  /**
   * The color of black dye.
   */
  BLACK(DYE_BLACK),

  /**
   * The color of red dye.
   */
  RED(DYE_RED),

  /**
   * The color of green dye.
   */
  GREEN(DYE_GREEN),

  /**
   * The color of brown dye.
   */
  BROWN(DYE_BROWN),

  /**
   * The color of blue dye.
   */
  BLUE(DYE_BLUE),

  /**
   * The color of purple dye.
   */
  PURPLE(DYE_PURPLE),

  /**
   * The color of cyan dye.
   */
  CYAN(DYE_CYAN),

  /**
   * The color of light gray dye.
   */
  LIGHT_GRAY(DYE_LIGHT_GRAY),

  /**
   * The color of gray dye.
   */
  GRAY(DYE_GRAY),

  /**
   * The color of pink dye.
   */
  PINK(DYE_PINK),

  /**
   * The color of lime dye.
   */
  LIME(DYE_LIME),

  /**
   * The color of yellow dye.
   */
  YELLOW(DYE_YELLOW),

  /**
   * The color of blue dye.
   */
  LIGHT_BLUE(DYE_LIGHT_BLUE),

  /**
   * The color of magenta dye.
   */
  MAGENTA(DYE_MAGENTA),

  /**
   * The color of orange dye.
   */
  ORANGE(DYE_ORANGE),

  /**
   * The color of white dye.
   */
  WHITE(DYE_WHITE);

  /**
   * The contained color.
   */
  private final MCColor color;
  private final int rgb;

  VanillaColors(MCColor color) {

    this.color = color;
    this.rgb = color.getRGB();
  }

}
