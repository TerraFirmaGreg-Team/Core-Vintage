package su.terrafirmagreg.api.library;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Arrays;

import static su.terrafirmagreg.modules.core.ConfigCore.MISC;

/**
 * This class is used to represent a date in the minecraft world.
 */
@Getter
public class MCDate {


  public static final long TICKS_IN_SECOND = 20L;
  public static final long TICKS_IN_MINUTE = 1200L;
  public static final long TICKS_IN_HOUR = 72000L;
  public static final long TICKS_IN_DAY = 24000L;

  /**
   * The total amount of days.
   */
  private long totalDays = 0;

  /**
   * The year represented.
   */
  private int year = 0;

  /**
   * The month represented.
   */
  private int month = 0;

  /**
   * The day represented.
   */
  private long day = 0;

  /**
   * Constructs a date for the current world time.
   *
   * @param world The world to get a date for. Different worlds may have different times.
   */
  public MCDate(World world) {

    this(world.getWorldTime());
  }

  /**
   * Base constructor for MCDate.
   *
   * @param time The amount of ticks to represent.
   */
  public MCDate(long time) {

    this.totalDays = (time + TICKS_IN_DAY) / TICKS_IN_DAY;
    this.day = this.totalDays;

    while (this.day > Month.valueOf(this.month).getMonthDays()) {

      this.day -= Month.valueOf(this.month).getMonthDays();

      this.month++;

      if (this.month == Month.getLength()) {

        this.month = 0;
        this.year++;
      }
    }
  }

  /**
   * Gets the month represented.
   *
   * @return The represented month.
   */
  public int getMonth() {

    return this.month + 1;
  }

  /**
   * Gets the localized day name.
   *
   * @return The localized day name.
   */
  @SideOnly(Side.CLIENT)
  public String getLocalizedDayName() {

    return I18n.format("time.tfg.day." + this.getDayName() + ".name");
  }

  /**
   * Gets the name of the represented day in a week.
   *
   * @return The represented day of the week.
   */
  public String getDayName() {

    return Week.valueOf((int) this.day).getName();
  }

  /**
   * Gets the localized month name.
   *
   * @return The localized month name.
   */
  @SideOnly(Side.CLIENT)
  public String getLocalizedMonthName() {

    return I18n.format("time.tfg.month." + this.getMonthName() + ".name");
  }

  /**
   * Gets the name of the represented month.
   *
   * @return The name of the represented month.
   */
  public String getMonthName() {

    return Month.valueOf(this.month).getName();
  }

  public enum Week implements IStringSerializable {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    private static final Week[] VALUES = values();

    Week() {}

    @NotNull
    public static Week valueOf(int id) {
      return id >= 0 && id < VALUES.length ? VALUES[id] : SUNDAY;
    }

    public Week next() {
      return VALUES[(ordinal() + 1) % VALUES.length];
    }

    @Override
    public @NotNull String getName() {
      return name().toLowerCase();
    }
  }

  @Getter
  public enum Month implements IStringSerializable {
    JANUARY(MISC.CALENDAR.MONTH.JANUARY.days, MISC.CALENDAR.MONTH.JANUARY.dayDuration, 66.5f),
    FEBRUARY(MISC.CALENDAR.MONTH.FEBRUARY.days, MISC.CALENDAR.MONTH.FEBRUARY.dayDuration, 65.5f),
    MARCH(MISC.CALENDAR.MONTH.MARCH.days, MISC.CALENDAR.MONTH.MARCH.dayDuration, 56f),
    APRIL(MISC.CALENDAR.MONTH.APRIL.days, MISC.CALENDAR.MONTH.APRIL.dayDuration, 47.5f),
    MAY(MISC.CALENDAR.MONTH.MAY.days, MISC.CALENDAR.MONTH.MAY.dayDuration, 38f),
    JUNE(MISC.CALENDAR.MONTH.JUNE.days, MISC.CALENDAR.MONTH.JUNE.dayDuration, 29.5f),
    JULY(MISC.CALENDAR.MONTH.JULY.days, MISC.CALENDAR.MONTH.JULY.dayDuration, 27f),
    AUGUST(MISC.CALENDAR.MONTH.AUGUST.days, MISC.CALENDAR.MONTH.AUGUST.dayDuration, 29.5f),
    SEPTEMBER(MISC.CALENDAR.MONTH.SEPTEMBER.days, MISC.CALENDAR.MONTH.SEPTEMBER.dayDuration, 38f),
    OCTOBER(MISC.CALENDAR.MONTH.OCTOBER.days, MISC.CALENDAR.MONTH.OCTOBER.dayDuration, 47.5f),
    NOVEMBER(MISC.CALENDAR.MONTH.NOVEMBER.days, MISC.CALENDAR.MONTH.NOVEMBER.dayDuration, 56f),
    DECEMBER(MISC.CALENDAR.MONTH.DECEMBER.days, MISC.CALENDAR.MONTH.DECEMBER.dayDuration, 65.5f);

    private static final Month[] VALUES = values();
    public static final float AVERAGE_TEMPERATURE_MODIFIER = (float) Arrays.stream(VALUES)
                                                                           .mapToDouble(Month::getTemperatureModifier)
                                                                           .average()
                                                                           .orElse(0);

    private final int monthDays;
    private final int daylightlength;
    private final float temperatureModifier;

    Month(int monthDays, int daylightlength, float temperatureModifier) {
      this.monthDays = monthDays;
      this.daylightlength = daylightlength;
      this.temperatureModifier = temperatureModifier;
    }

    @NotNull
    public static Month valueOf(int id) {
      return id >= 0 && id < VALUES.length ? VALUES[id] : JANUARY;
    }

    public static int getLength() {
      return VALUES.length;
    }

    public Month next() {
      return VALUES[(ordinal() + 1) % VALUES.length];
    }

    public boolean isWithin(Month lowerBoundInclusive, Month upperBoundInclusive) {
      if (lowerBoundInclusive.ordinal() <= upperBoundInclusive.ordinal()) {
        return this.ordinal() >= lowerBoundInclusive.ordinal() && this.ordinal() <= upperBoundInclusive.ordinal();
      }
      // If comparing the range NOV - FEB (for example), then both above and below count
      return this.ordinal() >= lowerBoundInclusive.ordinal() || this.ordinal() <= upperBoundInclusive.ordinal();
    }

    @Override
    public @NotNull String getName() {
      return name().toLowerCase();
    }
  }

}
