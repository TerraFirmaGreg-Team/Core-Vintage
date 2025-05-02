package su.terrafirmagreg.modules.animal.config;

import net.minecraftforge.common.config.Config.Comment;

public final class ConfigAnimalFeature {

  @Comment("Egg settings")
  public final ConfigFeatureEgg EGG = new ConfigFeatureEgg();

  public static final class ConfigFeatureEgg {

    @Comment({
      "If true, egg enable ",
      "Default = true"
    })
    public boolean enable = true;
  }
}
