package su.terrafirmagreg.modules.soil.config;

import net.minecraftforge.common.config.Config;

public final class ConfigSoilMisc {


  @Config.Comment("If true, grass and foliage will be slightly varied in color.")
  public boolean noiseEnable = true;

  @Config.Comment("If true, grass and foliage will be colored seasonally.")
  public boolean seasonColorEnable = true;

  @Config.Comment("The noise scale. Default = 10")
  public float noiseScale = 10f;

  @Config.Comment("How many darkness levels should the noise have? Default = 5")
  public int noiseLevels = 5;

  @Config.Comment("How potent should the darkness be? Default = 0.15")
  public float noiseDarkness = 0.15f;

  @Config.Comment("ARGB code for summer coloring in hexadecimal. Default: 1155FF44")
  public String seasonColorSummer = "1155FF44";

  @Config.Comment("ARGB code for summer coloring in hexadecimal. Default: 55FFDD44")
  public String seasonColorAutumn = "55FFDD44";

  @Config.Comment("ARGB code for winter coloring in hexadecimal. Default: 335566FF")
  public String seasonColorWinter = "335566FF";

  @Config.Comment("ARGB code for spring coloring in hexadecimal. Default: 3355FFBB")
  public String seasonColorSpring = "3355FFBB";
}
