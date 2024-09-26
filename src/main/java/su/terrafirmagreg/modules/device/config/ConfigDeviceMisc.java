package su.terrafirmagreg.modules.device.config;

import net.minecraftforge.common.config.Config;

public final class ConfigDeviceMisc {

  public final Debug DEBUG = new Debug();

  public static final class Debug {

    @Config.Name("Debug Mode")
    public boolean enable = true;
  }
}
