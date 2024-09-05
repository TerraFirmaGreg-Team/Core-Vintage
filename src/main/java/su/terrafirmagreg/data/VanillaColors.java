package su.terrafirmagreg.data;

import su.terrafirmagreg.data.lib.MCColor;


import lombok.Getter;

/**
 * Contains an enum of all the dye colors as RGB.
 */
@Getter
@SuppressWarnings("unused")
public enum VanillaColors {

  /**
   * The color of black dye.
   */
  BLACK(new MCColor(25, 25, 25)),

  /**
   * The color of red dye.
   */
  RED(new MCColor(153, 51, 51)),

  /**
   * The color of green dye.
   */
  GREEN(new MCColor(102, 127, 51)),

  /**
   * The color of brown dye.
   */
  BROWN(new MCColor(102, 76, 51)),

  /**
   * The color of blue dye.
   */
  BLUE(new MCColor(51, 76, 178)),

  /**
   * The color of purple dye.
   */
  PURPLE(new MCColor(127, 63, 178)),

  /**
   * The color of cyan dye.
   */
  CYAN(new MCColor(76, 127, 153)),

  /**
   * The color of light gray dye.
   */
  LIGHT_GRAY(new MCColor(153, 153, 153)),

  /**
   * The color of gray dye.
   */
  GRAY(new MCColor(76, 76, 76)),

  /**
   * The color of pink dye.
   */
  PINK(new MCColor(242, 127, 165)),

  /**
   * The color of lime dye.
   */
  LIME(new MCColor(127, 204, 25)),

  /**
   * The color of yellow dye.
   */
  YELLOW(new MCColor(229, 229, 51)),

  /**
   * The color of blue dye.
   */
  LIGHT_BLUE(new MCColor(102, 153, 216)),

  /**
   * The color of magenta dye.
   */
  MAGENTA(new MCColor(178, 76, 216)),

  /**
   * The color of orange dye.
   */
  ORANGE(new MCColor(216, 127, 5)),

  /**
   * The color of white dye.
   */
  WHITE(new MCColor(255, 255, 255));

  /**
   * The contained color.
   */
  private final MCColor color;

  VanillaColors(MCColor color) {

    this.color = color;
  }

}
