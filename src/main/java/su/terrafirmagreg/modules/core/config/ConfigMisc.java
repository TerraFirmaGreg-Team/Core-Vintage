package su.terrafirmagreg.modules.core.config;

import su.terrafirmagreg.api.data.enums.HealthDisplayFormat;
import su.terrafirmagreg.api.data.enums.OreTooltipMode;
import su.terrafirmagreg.api.data.enums.TimeTooltipMode;

import net.minecraftforge.common.config.Config;

public final class ConfigMisc {

  @Config.Comment("Weight settings")
  public final Weight WEIGHT = new Weight();

  @Config.Comment("Heat settings")
  public final Heat HEAT = new Heat();

  @Config.Comment("Ambiental settings")
  public final Ambiental AMBIENTAL = new Ambiental();

  @Config.Comment("Damage settings")
  public final Damage DAMAGE = new Damage();

  @Config.Comment("Debug settings")
  public final Debug DEBUG = new Debug();

  @Config.Comment("Calendar settings")
  public final Calendar CALENDAR = new Calendar();

  @Config.Comment("Fallable settings")
  public final Fallable FALLABLE = new Fallable();

  @Config.Comment("Display settings")
  public final Display DISPLAY = new Display();

  @Config.Comment("Tooltip settings")
  public final Tooltip TOOLTIP = new Tooltip();

  public static final class Weight {

    @Config.RequiresMcRestart
    @Config.Comment("Stack size of Very Heavy items. I wouldn't change this one. Default = 1")
    public int veryHeavy = 1;

    @Config.RequiresMcRestart
    @Config.Comment("Stack size of Heavy items. Default = 4")
    public int heavy = 4;

    @Config.RequiresMcRestart
    @Config.Comment("Stack size of Medium items. Default = 16")
    public int medium = 16;

    @Config.RequiresMcRestart
    @Config.Comment("Stack size of Light items. Default = 32")
    public int light = 32;

    @Config.RequiresMcRestart
    @Config.Comment("Stack size of Very Light items. Default = 64")
    public int veryLight = 64;
  }

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

  public static final class Ambiental {

    @Config.Comment({
      "If true, temperature is displayed in Celsius instead of Farhenheit.",
      "Default = true"
    })
    public boolean celsius = true;

    @Config.Comment({
      "If true, you will get extra details about your temperature when sneaking, when false they are always visible.",
      "Default = true"
    })
    public boolean sneakyDetails = true;

    @Config.Comment({
      "How quickly temperature rises and decreases. ",
      "Default = 1.0"
    })
    public float temperatureMultiplier = 1.0f;

    @Config.Comment({
      "How fast does temperature change when it's going towards the average. ",
      "Default = 5"
    })
    public float positiveModifier = 5f;

    @Config.Comment({
      "How fast does temperature change when it's going away from the average. ",
      "If you think you are giving yourself a challenge by increasing this number, think twice. ",
      "It makes it so that you have to warm yourself up every so often. ",
      "Default = 1"
    })
    public float negativeModifier = 1f;

    @Config.Comment({
      "How many ticks between modifier calculations. Too high values help performance but behave weirdly. ",
      "20 = 1 second means modifiers are checked every second. Also affects the packet sending interval. ",
      "Default = 20"
    })
    public int tickInterval = 20;

    @Config.Comment({
      "How potent are multipliers with more than one instance. (Eg. 2 fire pits nearby means they have 2 * this effectiveness). ",
      "Default = 0.7"
    })
    public float diminishedModifierMultiplier = 0.7f;

    @Config.Comment({
      "If true, you will start taking damage when below freezing or above burning temperatures. ",
      "Default = true"
    })
    public boolean takeDamage = true;

    @Config.Comment({
      "If true, you will start losing hunger when below cold temperatures and losing thirst when above hot temperatures. ",
      "Default = true"
    })
    public boolean loseHungerThirst = true;

    @Config.Comment({
      "How many modifiers of the same type until they stop adding together. ",
      "Default = 4"
    })
    public int modifierCap = 4;

    @Config.Comment({
      "If true, temperate areas won't be as mild. ",
      "Default = true"
    })
    public boolean harsherTemperateAreas = true;

    @Config.Comment({
      "If harsherTemperateAreas is true, environmental temperatures going away from the average are multiplied by this number. ",
      "(The less temperate an area is, the less the modifier affects it). ",
      "Default = 1.2 "
    })
    public float harsherMultiplier = 1.2f;

    @Config.Comment({
      "The temperature at which you are at equilibrium. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 20"
    })
    @Config.RangeDouble(min = 0F, max = 30F)
    public float averageTemperature = 20f;

    @Config.Comment({
      "The temperature at which your screen starts heating. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 30"
    })
    public float hotTemperature = 30f;

    @Config.Comment({
      "The temperature at which your screen starts freezing. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 10"
    })
    public float coldTemperature = 10f;

    @Config.Comment({
      "The temperature at which you start burning and taking damage. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 35"
    })
    public float burningTemperature = 35f;

    @Config.Comment({
      "The temperature at which you start freezing and taking damage. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 5"
    })
    public float freezingTemperature = 5f;

    @Config.Comment({
      "The temperature that nano or quantum armor will do when you are in a full set.",
      "Default = 20"
    })
    public int nanoOrQuarkTemp = 20;

  }

  public static final class Damage {

    @Config.Comment("Damage Source Types that will default to Slashing damage.")
    public String[] slashingSources = new String[]{

    };

    @Config.Comment("Damage Source Types that will default to Piercing damage.")
    public String[] piercingSources = new String[]{
      "arrow", "cactus", "thorns"
    };

    @Config.Comment("Damage Source Types that will default to Crushing damage.")
    public String[] crushingSources = new String[]{
      "anvil", "falling_block"
    };

    @Config.Comment("Damage Source Entities that will default to Slashing damage.")
    public String[] slashingEntities = new String[]{
      "minecraft:wither_skeleton", "minecraft:vex", "minecraft:vindication_illager",
      "minecraft:zombie_pigman", "minecraft:wolf", "minecraft:polar_bear"
    };

    @Config.Comment("Damage Source Entities that will default to Piercing damage.")
    public String[] piercingEntities = new String[]{
      "minecraft:stray", "minecraft:skeleton"
    };

    @Config.Comment("Damage Source Entities that will default to Crushing damage.")
    public String[] crushingEntities = new String[]{
      "minecraft:husk", "minecraft:skeleton_horse", "minecraft:zombie_horse",
      "minecraft:spider", "minecraft:giant",
      "minecraft:zombie", "minecraft:slime", "minecraft:cave_spider", "minecraft:silverfish",
      "minecraft:villager_golem", "minecraft:zombie_villager"
    };
  }

  public static final class Debug {

    @Config.Name("Debug Mode")
    @Config.Comment("When enabled, prints debug values to console. Activates some extra wand features. Enables extra item tooltips.")
    public boolean enable = true;

    @Config.Comment("Debug pathfinding")
    @Config.RequiresWorldRestart
    public boolean debugCreatePath = false;

    @Config.Comment({"Debug worldgen (the danger part) " +
                     "This will glass maps at max world height to help debug world gen. " +
                     "THIS WILL MESS UP YOUR WORLD!"})
    @Config.RequiresWorldRestart
    public boolean debugWorldGenDanger = false;

    @Config.Comment("Debug worldgen (safe part) " +
                    "This will output map images of world gen steps and print some debug info. " +
                    "This is safe to use.")
    @Config.RequiresWorldRestart
    public boolean debugWorldGenSafe = false;
  }

  public static final class Calendar {

    @Config.Comment({"Add Special Days!"})
    public final Birthday BIRTHDAYS = new Birthday();
    public final Month MONTH = new Month();

    public static class Month {

      public final January JANUARY = new January();

      public final February FEBRUARY = new February();

      public final March MARCH = new March();

      public final April APRIL = new April();

      public final May MAY = new May();

      public final June JUNE = new June();

      public final July JULY = new July();

      public final August AUGUST = new August();

      public final September SEPTEMBER = new September();

      public final October OCTOBER = new October();

      public final November NOVEMBER = new November();

      public final December DECEMBER = new December();

      @Config.Comment({
        "The default length of a month (in days) when a new world is started. " +
        "This can be changed in existing worlds via the /timetfc command."
      })
      @Config.RangeInt(min = 1, max = 31)
      public int defaultMonthLength = 8;

      @Config.Comment({"Constant duration for each Minecraft day"})
      @Config.RangeInt(min = 1)
      public int defaultDayDuration = 12000;

      @Config.Comment({"Constant duration for each Minecraft night"})
      @Config.RangeInt(min = 1)
      public int defaultNightDuration = 12000;

      @Config.Comment({"Whether the moon should be the one affected by staticAngle. Setting this to false will make the sun be static instead"})
      public boolean staticMoon = false;

      @Config.Comment({"Statically sets the sun/moon to a specific angle, can be used for infinite day/night. Set to -1 to disable"})
      @Config.RangeInt(min = -1, max = 180)
      public int staticAngle = -1;

      public static class January {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 31;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 10800;
      }

      public static class February {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 28;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 12000;
      }

      public static class March {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 31;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 13200;
      }

      public static class April {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 30;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 14400;
      }

      public static class May {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 31;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 15600;
      }

      public static class June {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 30;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 16800;
      }

      public static class July {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 31;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 18000;
      }

      public static class August {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 31;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 16800;
      }

      public static class September {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 30;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 15600;
      }

      public static class October {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 31;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 14400;
      }

      public static class November {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 30;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 13200;
      }

      public static class December {

        @Config.RangeInt(min = 1, max = 31)
        public int days = 31;

        @Config.RangeInt(min = 1, max = 18000)
        @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
        public int dayDuration = 12000;
      }

    }

    public static class Birthday {

      @Config.Comment({"Add special days to the TFC calendar!", "Format: MONTH<dayNumber> <name of your day>.",
                       "An invalid day string won't be read by the calendar. See defaults for examples."})
      @Config.RequiresMcRestart
      public String[] dayList = new String[]{
        "MARCH2 Firedale's Birthday"
      };
    }


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

    @Config.Comment({"Is the control key needed to show the advanced data?"})
    public boolean requireCTRL = true;

    @Config.Comment({"Show ItemStack tool classes when advanced tooltips are enabled. (F3+H)"})
    public boolean showToolClass = true;

    @Config.Comment({"Show ItemStack OreDictionary matches when advanced tooltips are enabled. (F3+H)"})
    public boolean showOreDictionary = true;

    @Config.Comment({"Show ItemStack Code Name when advanced tooltips are enabled. (F3+H)"})
    public boolean showCodeName = true;

    @Config.Comment({"Show ItemStack Metadata when advanced tooltips are enabled. (F3+H)"})
    public boolean showMetadata = true;

    @Config.Comment({"Show ItemStack Meta's Unlocalized Name when advanced tooltips are enabled. (F3+H)"})
    public boolean showMetaUnlocalizedName = true;

    @Config.Comment({"Show ItemStack NBT on the tooltip when advanced tooltips are enabled. (F3+H)"})
    public boolean showNBT = true;

    @Config.Comment({"Number of characters for NBT data"})
    @Config.RangeInt(min = 1, max = 500)
    public int charLimitNBT = 40;
  }
}
