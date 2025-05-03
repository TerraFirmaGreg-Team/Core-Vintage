package su.terrafirmagreg.modules.core.feature.falling;

import net.minecraftforge.common.config.Config;

public class FeatureFallingConfig {


  @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never fall.")
  public boolean enable = true;

  @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never destroy ore blocks.")
  public boolean destroyOres = true;

  @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never destroy loose items.")
  public boolean destroyItems = true;

  @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never hurt entities.")
  public boolean hurtEntities = true;

  @Config.Comment("Chance that mining raw rocks triggers a collapse.")
  @Config.RangeDouble(min = 0, max = 1)
  public double collapseChance = 0.1;

  @Config.Comment("Chance that collapsing blocks propagate the collapse. Influenced by distance from epicenter of collapse.")
  @Config.RangeDouble(min = 0, max = 1)
  public double propagateCollapseChance = 0.55;

  @Config.Comment("Horizontal radius of the support range of support beams.")
  @Config.RangeInt(min = 0, max = 8)
  public int supportBeamRangeHor = 4;

  @Config.Comment("Upwards support range of support beams.")
  @Config.RangeInt(min = 0, max = 3)
  public int supportBeamRangeUp = 1;

  @Config.Comment("Downwards support range of support beams.")
  @Config.RangeInt(min = 0, max = 3)
  public int supportBeamRangeDown = 1;

  @Config.Comment("Should chiseling raw stone blocks cause collapses?")
  public boolean chiselCausesCollapse = true;

  @Config.Comment("Should exploding raw stone blocks cause collapses?")
  public boolean explosionCausesCollapse = true;
}
