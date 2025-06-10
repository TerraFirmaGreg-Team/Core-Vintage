package su.terrafirmagreg.modules.core.feature.size;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

public class FeatureSizeConfig {


  @Comment({
    "If true, size and weight enable ",
    "Default = true"
  })
  public boolean enabled = true;

  @RequiresMcRestart
  @Comment("Stack size of Very Heavy items. I wouldn't change this one. Default = 1")
  public int veryHeavy = 1;

  @RequiresMcRestart
  @Comment("Stack size of Heavy items. Default = 4")
  public int heavy = 4;

  @RequiresMcRestart
  @Comment("Stack size of Medium items. Default = 16")
  public int medium = 16;

  @RequiresMcRestart
  @Comment("Stack size of Light items. Default = 32")
  public int light = 32;

  @RequiresMcRestart
  @Comment("Stack size of Very Light items. Default = 64")
  public int veryLight = 64;
}
