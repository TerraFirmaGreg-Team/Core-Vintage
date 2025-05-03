package su.terrafirmagreg.modules.core.feature.calendar.spi;

import su.terrafirmagreg.api.library.MCDate.Week;
import su.terrafirmagreg.api.util.TranslatorUtils;

import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface ICalendarFormatted extends ICalendar {
  /* Total calculation methods */

  static long getTotalMonths(long time, long daysInMonth) {
    return time / (daysInMonth * TICKS_IN_DAY);
  }

  static long getTotalYears(long time, long daysInMonth) {
    return 1000 + (time / (12 * daysInMonth * TICKS_IN_DAY));
  }

  /* Fraction Calculation Methods */

  static int getMinuteOfHour(long time) {
    return (int) ((time % TICKS_IN_HOUR) / TICKS_IN_MINUTE);
  }

  static int getHourOfDay(long time) {
    return (int) ((time / TICKS_IN_HOUR) % HOURS_IN_DAY);
  }

  static int getDayOfMonth(long time, long daysInMonth) {
    return 1 + (int) ((time / TICKS_IN_DAY) % daysInMonth);
  }

  /* Format Methods */

  static String getTimeAndDate(long time, long daysInMonth) {
    return getTimeAndDate(getHourOfDay(time), getMinuteOfHour(time), getMonthOfYear(time, daysInMonth), getDayOfMonth(time, daysInMonth), getTotalYears(time, daysInMonth));
  }

  static String getTimeAndDate(int hour, int minute, Month month, int day, long years) {
    String monthName = getMonthName(month, false);
    return getDate(hour, minute, monthName, day, years);
  }

  @Nonnull
  static Month getMonthOfYear(long time, long daysInMonth) {
    return Month.valueOf((int) ((time / (TICKS_IN_DAY * daysInMonth)) % 12));
  }

  static String getMonthName(Month month, boolean useSeasons) {
    return TranslatorUtils.translate(useSeasons ? TranslatorUtils.getEnumName("season", month) : TranslatorUtils.getEnumName(month));
  }

  static String getDate(int hour, int minute, String monthName, int day, long years) {
    // We call an additional String.format for the time, because vanilla doesn't support %02d format specifiers
    return TranslatorUtils.translate("tooltip.tfg.core.calendar.full_date", String.format("%02d:%02d", hour, minute), monthName, day, years);
  }

  static String getDayName(int dayOfMonth, long totalDays) {
    String date = Calendar.CALENDAR_TIME.getMonthOfYear().name() + dayOfMonth;
    String birthday = Calendar.BIRTHDAYS.get(date);
    if (birthday != null) {
      return birthday;
    }

    return TranslatorUtils.translate(TranslatorUtils.getEnumName("day", Week.valueOf((int) (totalDays % 7))));
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

  default String getTimeAndDate() {
    return getTimeAndDate(getTicks(), getDaysInMonth());
  }

  default String getSeasonDisplayName() {
    return getMonthName(getMonthOfYear(), true);
  }


  default String getDisplayDayName() {
    return getDayName(getDayOfMonth(), getTotalDays());
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
   * Calculates the current month from a calendar time
   */
  default Month getMonthOfYear() {
    return getMonthOfYear(getTicks(), getDaysInMonth());
  }

  /**
   * Calculates the day of a month from the calendar time (i.e. 01 - ??)
   */
  default int getDayOfMonth() {
    return getDayOfMonth(getTicks(), getDaysInMonth());
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
