package su.terrafirmagreg.modules.wood.config;

import net.minecraftforge.common.config.Config;

public final class ConfigWoodMisc {

  @Config.Comment({
          "Enable trees being fully cut by axes.",
          "Disable it if you want other mods to handle tree felling."})
  public boolean enableFelling = true;

  @Config.Comment("Enable smacking logs with a hammer giving sticks.")
  public boolean enableHammerSticks = true;

  @Config.Comment({
          "Should logs require tools (axes and saws, or hammers for sticks) to be cut? ",
          "If false, breaking logs with an empty hand will not drop logs."})
  public boolean requiresAxe = true;

  @Config.Comment("If false, leaves will not drop saplings.")
  public boolean enableSaplings = true;

  @Config.Comment("Chance per log for an item to drop when using a stone axe.")
  @Config.RangeDouble(min = 0, max = 1)
  public double stoneAxeReturnRate = 0.6;

  @Config.Comment("Normal leaf drop chance for sticks")
  @Config.RangeDouble(min = 0, max = 1)
  public double leafStickDropChance = 0.1;

  @Config.Comment("Chance that leaves will drop more sticks when harvested with configured tool classes.")
  @Config.RangeDouble(min = 0, max = 1)
  public double leafStickDropChanceBonus = 0.25;

  @Config.Comment("Tool classes that have the configured bonus to drop more sticks when harvesting leaves.")
  public String[] leafStickDropChanceBonusClasses = {
          "knife",
          "scythe"
  };
}
