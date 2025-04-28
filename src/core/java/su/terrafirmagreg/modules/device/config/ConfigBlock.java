package su.terrafirmagreg.modules.device.config;

import su.terrafirmagreg.api.data.enums.TimeTooltipMode;

import net.minecraftforge.common.config.Config;

import net.dries007.tfc.util.Alloy;

public final class ConfigBlock {

  @Config.Comment("Bellows")
  public final Bellows BELLOWS = new Bellows();

  @Config.Comment("Crucible")
  public final Crucible CRUCIBLE = new Crucible();

  @Config.Comment("Bloomery")
  public final Bloomery BLOOMERY = new Bloomery();

  @Config.Comment("Blast Furnace")
  public final BlastFurnace BLAST_FURNACE = new BlastFurnace();

  @Config.Comment("Charcoal Pit")
  public final CharcoalPit CHARCOAL_PIT = new CharcoalPit();

  @Config.Comment("Charcoal Forge")
  public final CharcoalForge CHARCOAL_FORGE = new CharcoalForge();

  @Config.Comment("Fire Pit")
  public final FirePit FIRE_PIT = new FirePit();

  @Config.Comment("Pit Kiln")
  public final PitKiln PIT_KILN = new PitKiln();

  @Config.Comment("Bear Trap")
  public final BearTrap BEAR_TRAP = new BearTrap();

  @Config.Comment("Snare")
  public final Snare SNARE = new Snare();

  @Config.Comment("Anvil")
  public final Anvil ANVIL = new Anvil();

  @Config.Comment("Oven")
  public final Oven OVEN = new Oven();

  @Config.Comment("Freeze Dryer")
  public final FreezeDryer FREEZE_DRYER = new FreezeDryer();

  @Config.Comment("Ice Bunker")
  public final IceBunker ICE_BUNKER = new IceBunker();

  @Config.Comment("Cellar Shelf")
  public final CellarShelf CELLAR_SHELF = new CellarShelf();

  public static final class Bellows {

    @Config.Comment(
      "The max number of air ticks, devices get a temperature bonus up to this amount. (1000 = 1 in game hour = 50 seconds), default is 600 ticks.")
    @Config.RangeInt(min = 0)
    public int maxTicks = 600;

    @Config.Comment("Amount of air ticks given to the connected devices. (1000 = 1 in game hour = 50 seconds), default is 200 ticks.")
    @Config.RangeInt(min = 0)
    public int ticks = 200;
  }

  public static final class Crucible {

    @Config.Comment("How much metal (units / mB) can a crucible hold?")
    @Config.RangeInt(min = 100, max = Alloy.SAFE_MAX_ALLOY)
    public int tank = 3_000;

    @Config.Comment("Let crucibles accept pouring metal (from small vessels / molds) from all 9 input slots at the same time.")
    public boolean enableAllSlots = false;

    @Config.Comment("How fast should crucibles accept fluids from molds / small vessel?")
    @Config.RangeInt(min = 1)
    public int pouringSpeed = 1;
  }

  public static final class Bloomery {

    @Config.Comment("Number of ticks required for a bloomery to complete. (1000 = 1 in game hour = 50 seconds), default is 15 hours.")
    @Config.RangeInt(min = 20)
    public int ticks = 15000;

    @Config.Comment({"Time tooltip info mode."})
    public TimeTooltipMode timeTooltipMode = TimeTooltipMode.MINECRAFT_HOURS;
  }

  public static final class BlastFurnace {

    @Config.Comment({"How fast the blast furnace consume fuels (compared to the charcoal forge).",
                     "Example: Charcoal (without bellows) lasts for 1800 ticks in forge while 1800 / 4 = 450 ticks in blast furnace."})
    @Config.RangeDouble(min = 0.1D)
    public double consumption = 4;
  }

  public static final class CharcoalPit {

    @Config.Comment("Number of ticks required for charcoal pit to complete. (1000 = 1 in game hour = 50 seconds), default is 18 hours.")
    public int ticks = 18_000;

    @Config.Comment("Can charcoal pits take glass (or stained glass) as a valid cover block?")
    public boolean canAcceptGlass = true;
  }

  public static final class CharcoalForge {

    @Config.Comment({
      "Number of burning ticks that is removed when the charcoal forge is on rain (random ticks).",
      "This effectively makes the charcoal forge consumes more fuel when it is raining above it."})
    @Config.RangeInt(min = 0)
    public int rainTicks = 600;
  }

  public static final class FirePit {

    @Config.Comment({
      "Number of ticks required for a cooking pot on a fire pit to boil before turning into soup, per serving. " +
      "(1000 = 1 in game hour = 50 seconds). Default is 1 hour."})
    @Config.RangeInt(min = 20)
    public int ticks = 1000;

    @Config.Comment({
      "Number of burning ticks that is removed when the fire pit is on rain (random ticks).",
      "This effectively makes the fire pit consumes more fuel when it is raining above it."})
    @Config.RangeInt(min = 0)
    public int rainTicks = 1000;
  }

  public static final class PitKiln {

    @Config.Comment("Number of ticks required for a pit kiln to burn out. (1000 = 1 in game hour = 50 seconds), default is 8 hours.")
    @Config.RangeInt(min = 20)
    public int ticks = 8000;

    @Config.Comment({"Time tooltip info mode."})
    public TimeTooltipMode timeTooltipMode = TimeTooltipMode.MINECRAFT_HOURS;
  }

  public static final class BearTrap {

    @Config.Comment({"Percent chance for a bear trap to break when harvested after being activated " +
                     "(a predator breakout will attempt to break the trap with double this chance)."})
    @Config.RangeDouble(min = 0.0D, max = 1.0D)
    public double breakChance = 0.1D;

    @Config.Comment({"The chance a predator has to break out of a bear trap each tick.",
                     "0 = no breakouts. If this number isn't kept very small then breakouts will happen very fast. 1 = instant breakout."})
    @Config.RangeDouble(min = 0.0D, max = 1.0D)
    public double breakoutChance = 0.001D;

    @Config.Comment({"The duration of the debuffs applied by the bear trap in ticks.",
                     "Set to 0 to disable the debuffs."})
    @Config.RangeInt(min = 0)
    public int debuffDuration = 1000;

    @Config.Comment({"The fraction of an entity's health that is dealt as damage when stepping in a trap.",
                     "E.g. 3 = 1/3 current health dealt as damage. Less than 1 will deal more damage than current health, probably an instakill. Set to 0 to do no damage."})
    @Config.RangeDouble(min = 0.0D, max = 20.0D)
    public double healthCut = 3.0D;

    @Config.Comment({"The amount of damage points dealt by a bear trap.",
                     "This will override the fractional health cut setting if set to a value greater than 0"})
    @Config.RangeDouble(min = 0.0D)
    public double fixedDamage = 0.0D;
  }

  public static final class Snare {

    @Config.Comment("Percent chance for a snare to break when harvested after being tripped.")
    @Config.RangeDouble(min = 0.0D, max = 1.0D)
    public double breakChance = 0.2D;

    @Config.Comment({
      "The chance for a snare to capture a random small animal when loaded with bait. Happens on random block ticks."
    })
    @Config.RangeDouble(min = 0.0D, max = 1.0D)
    public double baitCaptureChance = 0.05D;

    @Config.Comment({
      "The chance for a piece of bait in a snare to be consumed if the snare fails to capture a random animal."
    })
    @Config.RangeDouble(min = 0.0D, max = 1.0D)
    public double baitExpireChance = 0.05D;
  }

  public static final class Anvil {

    @Config.Comment("Range of pixels on either side of the working target that can be accepted to complete a smithing recipe")
    @Config.RangeInt(min = 0)
    public int acceptableAnvilRange = 0;
  }

  public static final class Oven {

    @Config.Comment({"Time tooltip info mode."})
    public TimeTooltipMode timeTooltipMode = TimeTooltipMode.MINECRAFT_HOURS;
  }

  public static final class FreezeDryer {

    @Config.Comment({
      "Maximum amount of coolant freeze dryer can store internally."
    })
    public float coolantMax = 6400F;

    @Config.Comment({
      "Number of seconds at target pressure to preserve."
    })
    public int sealedDuration = 120;

    @Config.Comment({
      "This is the sea level pressure."
    })
    public float seaLevelPressure = 1016F;

    @Config.Comment({
      "The multiplier 100 is 1.0, 123 is 1.23\t:\tIs used to effect the coolant consumption rate of the cellars"
    })
    public float coolantConsumptionMultiplier = 1.0F;
    @Config.Comment({
      "1000 is 10.0, 1230 is 12.3\t:\tPercentage of Temperature Delta in heat dissipated per second"
    })
    public float temperatureDissipation = 0.2F;
    @Config.Comment({
      "Work per redstone power level each second"
    })
    public float workPerPower = 100;
    @Config.Comment({
      "1000 is 1.00, 1230 is 1.23\t:\tTarget pressure to achieve to start preserving"
    })
    public float targetPressure = 0.6F;
    @Config.Comment({
      "1000 is 1.00, 1230 is 1.23\\t:\\tHeat generated per redstone power level"
    })
    public float heatPerPower = 0.1F;
    @Config.Comment({
      "1000 is 1.00, 1230 is 1.23\\t:\\tPressure change per Y level"
    })
    public float pressureChange = 1.980F;
    @Config.Comment({
      "Maximum temperature of freeze dryer vacuum pump"
    })
    public int maxTemp = 40;
    @Config.Comment("This setting dictates how much coolant you get from a block of Sea Ice")
    public int seaIceCoolant = 180;

    @Config.Comment("This setting dictates how much coolant you get from a block of Ice or Ice Shards")
    public int iceCoolant = 120;

    @Config.Comment("This setting dictates how much coolant you get from a block of Snow")
    public int snowCoolant = 60;

    @Config.Comment("This setting dictates how much coolant you get from a block of Packed Ice")
    public int packedIceCoolant = 60;

    @Config.Comment("This setting dictates how much coolant you get from a block of Snowball")
    public int snowBallCoolant = 15;
  }

  public static final class IceBunker {

    @Config.Comment("Will enable all debug text.")
    public boolean debug = false;

    @Config.Comment("This will cause the temperature calculation, for cellars, to be based on the average temperature of the month. Instead of actual current temperature")
    public boolean tempMonthAvg = false;

    @Config.Comment("Makes using sea ice and packed ice effect temperature of the cellars.")
    public boolean specialIceTraits = false;

    @Config.Comment("Is the minimum value the ice house can make it with out negative temperatures outside. Special Ice Traits do not take this into account.")
    public int iceHouseTemperature = 1;

    @Config.Comment("The multiplier 100 is 1.0, 123 is 1.23\t:\tIs used to effect the coolant consumption rate of the cellars")
    public float coolantConsumptionMultiplier = 1.0F;

    @Config.Comment("This setting dictates how much coolant you get from a block of Sea Ice")
    public int seaIceCoolant = 180;

    @Config.Comment("This setting dictates how much coolant you get from a block of Ice or Ice Shards")
    public int iceCoolant = 120;

    @Config.Comment("This setting dictates how much coolant you get from a block of Snow")
    public int snowCoolant = 60;

    @Config.Comment("This setting dictates how much coolant you get from a block of Packed Ice")
    public int packedIceCoolant = 60;

    @Config.Comment("This setting dictates how much coolant you get from a block of Snowball")
    public int snowBallCoolant = 15;

  }

  public static final class CellarShelf {

    @Config.Comment({
      "This is the temperature at which foods will gain a trait."
    })
    public int coolMaxThreshold = 20;

    @Config.Comment({
      "This is the temperature at which foods will go from icy to frozen."
    })
    public int frozenMaxThreshold = 0;

    @Config.Comment({
      "This is the temperature at which foods will go from cool to icy."
    })
    public int icyMaxThreshold = 5;

  }
}
