package su.terrafirmagreg.modules.animal.config;

import net.minecraftforge.common.config.Config;

public final class ConfigAnimalMisc {

  @Config.Name("Search distance")
  @Config.Comment("The distance for animals to search for food")
  public double searchDistance = 10;
}
