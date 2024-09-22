package su.terrafirmagreg.modules.wood.config;

import net.minecraftforge.common.config.Config;

public final class ConfigWoodEntity {

  @Config.RequiresMcRestart
  @Config.RangeDouble(min = -1.0D, max = 0.0D)
  public final double PULL_SPEED_MODIFIER = -0.65D;
}
