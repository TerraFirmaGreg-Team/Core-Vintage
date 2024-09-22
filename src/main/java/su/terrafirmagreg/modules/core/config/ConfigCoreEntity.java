package su.terrafirmagreg.modules.core.config;

import net.minecraftforge.common.config.Config;

public final class ConfigCoreEntity {

  @Config.Comment("Player settings")
  public final Player PLAYER = new Player();

  public static final class Player {

    @Config.RequiresMcRestart
    @Config.Comment("The hunger value with which a player respawns. Default = 100")
    @Config.RangeInt(min = 0, max = 100)
    public int respawnHungerLevel = 100;

    @Config.RequiresMcRestart
    @Config.Comment("The thirst value with which a player respawns. Default = 100")
    @Config.RangeInt(min = 0, max = 100)
    public int respawnThirstLevel = 100;
  }
}
