package su.terrafirmagreg.modules.device.config;

import net.minecraftforge.common.config.Config;

public final class ConfigDeviceItem {

  @Config.Comment("Water Flasks")
  public final WaterFlasks WATER_FLASKS = new WaterFlasks();

  @Config.Comment("Sling")
  public final Sling SLING = new Sling();

  public static final class WaterFlasks {

    @Config.Comment("Liquid Capacity of Leather Flask (500 = 1/2 bucket = 5 drinks or 2 water bars)")
    @Config.RangeInt(min = 100)
    public int leatherCap = 500;

    @Config.Comment("Liquid Capacity of Iron Flask (1000 = 1 bucket = 10 drinks or 4 water bars)")
    @Config.RangeInt(min = 100)
    public int ironCap = 2000;

    @Config.Comment("Damage Capability of Flasks are Capacity/(this value) 0 = MAXINT uses")
    @Config.RangeInt(min = 0)
    public int damageFactor = 5;

  }

  public static final class Sling {

    @Config.Comment({"Damage multiplier against predator animals and skeletons.", "New damage = sling damage * multiplier"})
    @Config.RangeDouble(min = 1.0D, max = 10.0D)
    public double predatorMultiplier = 2.0D;

    @Config.Comment({"The maximum power a sling can be charged up to.",
            "A fully charged sling will deal damage equal to maximum power, but projectile speed is fixed to the ratio: current power / max power."})
    @Config.RangeInt(min = 1)
    public int maxPower = 8;

    @Config.Comment({"The speed at which the sling charges.", "Value represents number of ticks per power level (lower = faster)."})
    @Config.RangeInt(min = 1)
    public int chargeSpeed = 16;
  }
}
