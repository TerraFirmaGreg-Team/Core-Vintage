package su.terrafirmagreg.modules.device.config;

import net.minecraftforge.common.config.Config;

import net.dries007.tfc.util.Alloy;

public final class ConfigDeviceBlock {

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

    @Config.Comment({"The duration of the debuffs applied by the bear trap in ticks.", "Set to 0 to disable the debuffs."})
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

    @Config.Comment({"The chance for a snare to capture a random small animal when loaded with bait. Happens on random block ticks."})
    @Config.RangeDouble(min = 0.0D, max = 1.0D)
    public double baitCaptureChance = 0.05D;

    @Config.Comment({"The chance for a piece of bait in a snare to be consumed if the snare fails to capture a random animal."})
    @Config.RangeDouble(min = 0.0D, max = 1.0D)
    public double baitExpireChance = 0.05D;
  }
}
