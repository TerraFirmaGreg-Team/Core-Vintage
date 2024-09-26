package net.dries007.tfc.util.calendar;

import su.terrafirmagreg.data.lib.MCDate.Month;

import net.minecraft.world.World;

import net.dries007.tfc.TerraFirmaCraft;

import org.jetbrains.annotations.NotNull;

public interface ICalendarFormatted extends ICalendar {
  /* Total calculation methods */

  static long getTotalMonths(long time, long daysInMonth) {
    return time / (daysInMonth * TICKS_IN_DAY);
  }

  static String getTimeAndDate(long time, long daysInMonth) {
    return getTimeAndDate(getHourOfDay(time), getMinuteOfHour(time), getMonthOfYear(time, daysInMonth), getDayOfMonth(time, daysInMonth),
                          getTotalYears(time, daysInMonth));
  }

  /* Fraction Calculation Methods */

  static String getTimeAndDate(int hour, int minute, Month month, int day, long years) {
    String monthName = TerraFirmaCraft.getProxy().getMonthName(month, false);
    return TerraFirmaCraft.getProxy().getDate(hour, minute, monthName, day, years);
  }

  static int getHourOfDay(long time) {
    return (int) ((time / TICKS_IN_HOUR) % HOURS_IN_DAY);
  }

  static int getMinuteOfHour(long time) {
    return (int) ((time % TICKS_IN_HOUR) / TICKS_IN_MINUTE);
  }

  /* Format Methods */

  @NotNull
  static Month getMonthOfYear(long time, long daysInMonth) {
    return Month.valueOf((int) ((time / (TICKS_IN_DAY * daysInMonth)) % 12));
  }

  static int getDayOfMonth(long time, long daysInMonth) {
    return 1 + (int) ((time / TICKS_IN_DAY) % daysInMonth);
  }

  static long getTotalYears(long time, long daysInMonth) {
    return 1000 + (time / (12 * daysInMonth * TICKS_IN_DAY));
  }

  default String getTimeAndDate() {
    return getTimeAndDate(getTicks(), getDaysInMonth());
  }

  /**
   * Gets the current time, according to the specific calendar's offset and settings
   *
   * @return a time
   */
  @Override
  long getTicks();

  /**
   * Gets the number of days in a month, from the calendar's instance values
   *
   * @return a number of days in a month
   */
  long getDaysInMonth();

  default String getSeasonDisplayName() {
    return TerraFirmaCraft.getProxy().getMonthName(getMonthOfYear(), true);
  }

  /**
   * Calculates the current month from a calendar time
   */
  default Month getMonthOfYear() {
    return getMonthOfYear(getTicks(), getDaysInMonth());
  }

  default String getDisplayDayName() {
    return TerraFirmaCraft.getProxy().getDayName(getDayOfMonth(), getTotalDays());
  }

  /**
   * Calculates the day of a month from the calendar time (i.e. 01 - ??)
   */
  default int getDayOfMonth() {
    return getDayOfMonth(getTicks(), getDaysInMonth());
  }

  /**
   * Get the total number of years for display (i.e 1000, 1001, etc.)
   */
  default long getTotalYears() {
    return getTotalYears(getTicks(), getDaysInMonth());
  }

  /**
   * Get the equivalent total world time World time 0 = 6:00 AM, which is calendar time 6000
   *
   * @return a value in [0, 24000) which should match the result of {@link World#getWorldTime()}
   */
  default long getWorldTime() {
    return (getTicks() - (6 * ICalendar.TICKS_IN_HOUR)) % ICalendar.TICKS_IN_DAY;
  }

  /**
   * Calculate the total amount of months
   *
   * @return an amount of months
   */
  default long getTotalMonths() {
    return getTicks() / (getDaysInMonth() * TICKS_IN_DAY);
  }

  /**
   * Calculates the hour of the day from a calendar time, military time (i.e 00 - 23)
   */
  default int getHourOfDay() {
    return getHourOfDay(getTicks());
  }

  /**
   * Calculates the minute of the hour from a calendar time (i.e. 00 - 59)
   */
  default int getMinuteOfHour() {
    return getMinuteOfHour(getTicks());
  }
}
