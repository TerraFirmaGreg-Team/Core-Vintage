package su.terrafirmagreg.modules.core.config;

import net.minecraftforge.common.config.Config;

public final class ConfigCoreItem {

  @Config.Comment("Fire starter")
  public final FireStarter FIRE_STARTER = new FireStarter();


  public static final class FireStarter {

    @Config.Comment("Chance for the fire starter to be successful. Default = 0.5")
    @Config.RangeDouble(min = 0d, max = 1d)
    public double chance = 0.5;
  }


}
