package su.terrafirmagreg.modules.food.config;

import su.terrafirmagreg.api.data.enums.DecayTooltipMode;

import net.minecraftforge.common.config.Config;

public final class ConfigFoodMisc {

  public final Decay DECAY = new Decay();

  public static final class Decay {

    @Config.Comment("Modifier for how quickly food will decay. Higher values = faster decay. Set to 0 for infinite expiration time")
    @Config.RangeDouble(min = 0, max = 10)
    public double modifier = 1.0;

    @Config.Comment({"The number of hours to which initial food decay will be synced. " +
                     "When a food item is dropped, it's initial expiration date will be rounded to the closest multiple of this (in hours)."})
    @Config.RangeInt(min = 1, max = 48)
    public int stackTime = 6;

    @Config.Comment("Food decay tooltip mode.")
    public DecayTooltipMode tooltipMode = DecayTooltipMode.ALL_INFO;

    @Config.Comment("The color to render on top of rotten food. Express as a 256 bit color value: 0xFFFFFF = white, 0x000000 = black")
    public int rottenFoodOverlayColor = 0x88CC33;
  }
}
