package su.terrafirmagreg.modules.core.config;

import su.terrafirmagreg.api.data.enums.OreTooltipMode;

import net.minecraftforge.common.config.Config;

public final class ConfigMisc {

  @Config.Comment("Weight settings")
  public final Weight WEIGHT = new Weight();

  @Config.Comment("Heat settings")
  public final Heat HEAT = new Heat();

  @Config.Comment("Ambiental settings")
  public final Ambiental AMBIENTAL = new Ambiental();

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
    public float harsherMultiplier = 1.20f;

    @Config.Comment({
      "The temperature at which you are at equilibrium. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 15"
    })
    @Config.RangeDouble(min = 0F, max = 30F)
    public float averageTemperature = 15f;

    @Config.Comment({
      "The temperature at which your screen starts heating. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 20"
    })
    public float hotTemperature = 20f;

    @Config.Comment({
      "The temperature at which your screen starts freezing. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 10"
    })
    public float coldTemperature = 10f;

    @Config.Comment({
      "The temperature at which you start burning and taking damage. ",
      "It's advisable to not change this by a lot since the entire ecosystem revolves around this. ",
      "Default = 25"
    })
    public float burningTemperature = 25f;

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
}
