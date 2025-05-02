package su.terrafirmagreg.modules.core.config;

import su.terrafirmagreg.api.data.enums.HealthDisplayFormat;
import su.terrafirmagreg.api.data.enums.OreTooltipMode;
import su.terrafirmagreg.api.data.enums.TimeTooltipMode;

import net.minecraftforge.common.config.Config;

public final class ConfigCoreMisc {

  @Config.Comment("Heat settings")
  public final Heat HEAT = new Heat();


  @Config.Comment("Damage settings")
  public final Damage DAMAGE = new Damage();

  @Config.Comment("Debug settings")
  public final Debug DEBUG = new Debug();


  @Config.Comment("Fallable settings")
  public final Fallable FALLABLE = new Fallable();

  @Config.Comment("Display settings")
  public final Display DISPLAY = new Display();

  @Config.Comment("Tooltip settings")
  public final Tooltip TOOLTIP = new Tooltip();

  public static final class Heat {

    @Config.Comment("Ore tooltip info mode.")
    public OreTooltipMode oreTooltipMode = OreTooltipMode.ALL_INFO;

    @Config.Comment("Modifier for how quickly items will gain or lose heat. Smaller number = slower temperature changes.")
    @Config.RangeDouble(min = 0, max = 10)
    public double globalModifier = 0.5;

    @Config.Comment(
      "Modifier for how quickly devices (i.e. charcoal forge, fire pit) will gain or lose heat. Smaller number = slower temperature changes.")
    @Config.RangeDouble(min = 0, max = 10)
    public double heatingModifier = 1;

    @Config.Comment("Can heatable items be cooled down in the world? Such as putting it in a pool of water or on top of some snow?")
    public boolean coolHeatablesInWorld = true;

    @Config.Comment("If heatable items can be cooled down in world, after how many ticks should the item attempt to be cooled down?")
    @Config.RangeInt(min = 1, max = 5999)
    public int ticksBeforeAttemptToCool = 10;
  }

  public static final class Damage {

    @Config.Comment("Damage Source Types that will default to Slashing damage.")
    public String[] slashingSources = new String[]{

    };

    @Config.Comment("Damage Source Types that will default to Piercing damage.")
    public String[] piercingSources = new String[]{
      "arrow",
      "cactus",
      "thorns"
    };

    @Config.Comment("Damage Source Types that will default to Crushing damage.")
    public String[] crushingSources = new String[]{
      "anvil",
      "falling_block"
    };

    @Config.Comment("Damage Source Entities that will default to Slashing damage.")
    public String[] slashingEntities = new String[]{
      "minecraft:wither_skeleton",
      "minecraft:vex",
      "minecraft:vindication_illager",
      "minecraft:zombie_pigman",
      "minecraft:wolf",
      "minecraft:polar_bear"
    };

    @Config.Comment("Damage Source Entities that will default to Piercing damage.")
    public String[] piercingEntities = new String[]{
      "minecraft:stray",
      "minecraft:skeleton"
    };

    @Config.Comment("Damage Source Entities that will default to Crushing damage.")
    public String[] crushingEntities = new String[]{
      "minecraft:husk",
      "minecraft:skeleton_horse",
      "minecraft:zombie_horse",
      "minecraft:spider",
      "minecraft:giant",
      "minecraft:zombie",
      "minecraft:slime",
      "minecraft:cave_spider",
      "minecraft:silverfish",
      "minecraft:villager_golem",
      "minecraft:zombie_villager"
    };
  }

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

  public static final class Fallable {

    @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never fall.")
    public boolean enable = true;

    @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never destroy ore blocks.")
    public boolean destroyOres = true;

    @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never destroy loose items.")
    public boolean destroyItems = true;

    @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never hurt entities.")
    public boolean hurtEntities = true;

    @Config.Comment("Chance that mining raw rocks triggers a collapse.")
    @Config.RangeDouble(min = 0, max = 1)
    public double collapseChance = 0.1;

    @Config.Comment("Chance that collapsing blocks propagate the collapse. Influenced by distance from epicenter of collapse.")
    @Config.RangeDouble(min = 0, max = 1)
    public double propagateCollapseChance = 0.55;

    @Config.Comment("Horizontal radius of the support range of support beams.")
    @Config.RangeInt(min = 0, max = 8)
    public int supportBeamRangeHor = 4;

    @Config.Comment("Upwards support range of support beams.")
    @Config.RangeInt(min = 0, max = 3)
    public int supportBeamRangeUp = 1;

    @Config.Comment("Downwards support range of support beams.")
    @Config.RangeInt(min = 0, max = 3)
    public int supportBeamRangeDown = 1;

    @Config.Comment("Should chiseling raw stone blocks cause collapses?")
    public boolean chiselCausesCollapse = true;

    @Config.Comment("Should exploding raw stone blocks cause collapses?")
    public boolean explosionCausesCollapse = true;
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
