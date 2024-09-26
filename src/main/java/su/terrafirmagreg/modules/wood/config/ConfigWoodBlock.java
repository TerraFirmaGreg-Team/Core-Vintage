package su.terrafirmagreg.modules.wood.config;

import net.minecraftforge.common.config.Config;

public final class ConfigWoodBlock {

  @Config.Comment("Barrel")
  public final Barrel BARREL = new Barrel();

  @Config.Comment("Support")
  public final Support SUPPORT = new Support();

  public static final class Barrel {

    @Config.Comment("How much fluid (mB) can a barrel hold?")
    @Config.RangeInt(min = 100)
    public int tank = 10_000;

    @Config.Comment("List of fluids allowed to be inserted into a barrel.")
    public String[] fluidWhitelist = {
      "fresh_water",
      "hot_water",
      "salt_water",
      "water",
      "limewater",
      "tannin",
      "olive_oil",
      "olive_oil_water",
      "vinegar",
      "rum",
      "beer",
      "whiskey",
      "rye_whiskey",
      "corn_whiskey",
      "sake",
      "vodka",
      "cider",
      "brine",
      "milk",
      "milk_curdled",
      "milk_vinegar",
      "white_dye",
      "orange_dye",
      "magenta_dye",
      "light_blue_dye",
      "yellow_dye",
      "lime_dye",
      "pink_dye",
      "gray_dye",
      "light_gray_dye",
      "cyan_dye",
      "purple_dye",
      "blue_dye",
      "brown_dye",
      "green_dye",
      "red_dye",
      "black_dye"
    };
  }

  public static final class Support {

    @Config.Comment("Horizontal radius of the support range of support beams.")
    @Config.RangeInt(min = 0, max = 8)
    public int supportBeamRangeHor = 4;

    @Config.Comment("Upwards support range of support beams.")
    @Config.RangeInt(min = 0, max = 3)
    public int supportBeamRangeUp = 1;

    @Config.Comment("Downwards support range of support beams.")
    @Config.RangeInt(min = 0, max = 3)
    public int supportBeamRangeDown = 1;
  }
}
