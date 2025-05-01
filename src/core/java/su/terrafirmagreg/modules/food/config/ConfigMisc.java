package su.terrafirmagreg.modules.food.config;

import net.minecraftforge.common.config.Config;

public final class ConfigMisc {

  @Config.Comment("Debug settings")
  public final Debug DEBUG = new Debug();

  public static final class Debug {

    @Config.Name("Debug Mode")
    @Config.Comment("When enabled, prints debug values to console. Activates some extra wand features. Enables extra item tooltips.")
    public boolean enable = true;
  }

}
