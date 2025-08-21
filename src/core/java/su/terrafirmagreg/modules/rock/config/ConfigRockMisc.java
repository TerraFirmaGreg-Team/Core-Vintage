package su.terrafirmagreg.modules.rock.config;

import net.minecraftforge.common.config.Config;

public final class ConfigRockMisc {

  public final Ore ORE = new Ore();

  public static final class Ore {

    @Config.Comment("The amount of metal contained in a small ore / nugget.")
    @Config.RangeInt(min = 1, max = 10_000)
    public int smallOreMetalAmount = 10;

    @Config.Comment("The amount of metal contained in a poor ore.")
    @Config.RangeInt(min = 1, max = 10_000)
    public int poorOreMetalAmount = 15;

    @Config.Comment("The amount of metal contained in a normal ore.")
    @Config.RangeInt(min = 1, max = 10_000)
    public int normalOreMetalAmount = 25;

    @Config.Comment("The amount of metal contained in a rich ore.")
    @Config.RangeInt(min = 1, max = 10_000)
    public int richOreMetalAmount = 35;
  }

}
