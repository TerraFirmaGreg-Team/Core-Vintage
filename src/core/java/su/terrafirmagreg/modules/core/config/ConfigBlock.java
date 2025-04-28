package su.terrafirmagreg.modules.core.config;

import net.minecraftforge.common.config.Config;

public final class ConfigBlock {

  @Config.Comment("Puddle")
  public final Puddle PUDDLE = new Puddle();

  public static final class Puddle {

    @Config.Name("Puddle Rate")
    @Config.Comment({
      "The game will pick a random block every tick for every active chunk",
      "Then it will check if a puddle can be placed there",
      "Then it generates a random number between 0-99",
      "And if that number is less than this puddle rate number, it puts a puddle",
      "That means any value over 99 will flood your world with puddles"
    })
    @Config.RangeInt(min = 0, max = 99)
    public int puddleRate = 5;

    @Config.Name("Can Use Glass Bottle")
    @Config.Comment({"Toggles filling glass bottles with puddle water"})
    public boolean canUseGlassBottle = true;

  }
}
