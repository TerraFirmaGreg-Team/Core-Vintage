package su.terrafirmagreg.modules.core.config;

import su.terrafirmagreg.api.data.enums.HealthDisplayFormat;
import su.terrafirmagreg.api.data.enums.TimeTooltipMode;

import net.minecraftforge.common.config.Config;

public final class ConfigCoreMisc {


  @Config.Comment("Debug settings")
  public final Debug DEBUG = new Debug();
  

  @Config.Comment("Display settings")
  public final Display DISPLAY = new Display();

  @Config.Comment("Tooltip settings")
  public final Tooltip TOOLTIP = new Tooltip();


  public static final class Debug {

    @Config.Name("Debug Mode")
    @Config.Comment("When enabled, prints debug values to console. Activates some extra wand features. Enables extra item tooltips.")
    public boolean enable = true;

    @Config.Comment("Debug pathfinding")
    @Config.RequiresWorldRestart
    public boolean debugCreatePath = false;

    @Config.Comment({
      "Debug worldgen (the danger part) ",
      "This will glass maps at max world height to help debug world gen. ",
      "THIS WILL MESS UP YOUR WORLD!"
    })
    @Config.RequiresWorldRestart
    public boolean debugWorldGenDanger = false;

    @Config.Comment({
      "Debug worldgen (safe part) ",
      "This will output map images of world gen steps and print some debug info. ",
      "This is safe to use."
    })
    @Config.RequiresWorldRestart
    public boolean debugWorldGenSafe = false;
  }


  public static final class Display {

    @Config.Comment({"If TFC health bar is enabled, this changes display health format. (Default: TFC = 1000 / 1000)."})
    public HealthDisplayFormat healthDisplayFormat = HealthDisplayFormat.TFC;

    @Config.Comment({"Disable TFC health bar and use vanilla instead?"})
    public boolean useVanillaHealth = false;

    @Config.Comment({"Disable TFC hunger bar and use vanilla instead?"})
    public boolean useVanillaHunger = false;

    @Config.Comment({"Hide the thirst bar?"})
    public boolean hideThirstBar = false;
  }

  public static final class Tooltip {

    @Config.Comment({"Time tooltip info mode."})
    public TimeTooltipMode timeMode = TimeTooltipMode.MINECRAFT_HOURS;

  }
}
