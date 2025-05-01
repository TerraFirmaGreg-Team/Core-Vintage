package su.terrafirmagreg.modules.food.config;

import su.terrafirmagreg.api.data.enums.DecayTooltipMode;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;

public final class ConfigFeature {

  @Comment("Decay settings")
  public final Decay DECAY = new Decay();


  public static final class Decay {

    @Config.Comment("Food decay tooltip mode.")
    public DecayTooltipMode tooltipMode = DecayTooltipMode.ALL_INFO;


    @Config.Comment({
      "Modifier for how quickly food will decay.",
      " Higher values = faster decay. Set to 0 for infinite expiration time"
    })
    @Config.RangeDouble(min = 0, max = 10)
    public double modifier = 1.0;

    @Config.Comment("The number of hours to which initial food decay will be synced. When a food item is dropped, it's initial expiration date will be rounded to the closest multiple of this (in hours).")
    @Config.RangeInt(min = 1, max = 48)
    public int stackTime = 6;
  }
}
