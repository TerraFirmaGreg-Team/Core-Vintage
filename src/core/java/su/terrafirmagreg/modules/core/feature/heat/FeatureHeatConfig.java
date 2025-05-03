package su.terrafirmagreg.modules.core.feature.heat;

import su.terrafirmagreg.api.data.enums.OreTooltipMode;

import net.minecraftforge.common.config.Config;

public class FeatureHeatConfig {


  @Config.Comment("Ore tooltip info mode.")
  public OreTooltipMode oreTooltipMode = OreTooltipMode.ALL_INFO;

  @Config.Comment("Modifier for how quickly items will gain or lose heat. Smaller number = slower temperature changes.")
  @Config.RangeDouble(min = 0, max = 10)
  public double globalModifier = 0.5;

  @Config.Comment(
    "Modifier for how quickly devices (i.e. charcoal forge, fire pit) will gain or lose heat. Smaller number = slower temperature changes.")
  @Config.RangeDouble(min = 0, max = 10)
  public double heatingModifier = 1;

  @Config.Comment("Can heatable items be cooled down in the world? Such as putting it in a pool of water or on top of some snow?")
  public boolean coolHeatablesInWorld = true;

  @Config.Comment("If heatable items can be cooled down in world, after how many ticks should the item attempt to be cooled down?")
  @Config.RangeInt(min = 1, max = 5999)
  public int ticksBeforeAttemptToCool = 10;
}
