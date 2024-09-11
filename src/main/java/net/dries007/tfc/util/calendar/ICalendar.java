package net.dries007.tfc.util.calendar;

/**
 * The base interface for an object that tracks time
 */
public interface ICalendar {

  /* Constants */
  int TICKS_IN_HOUR = 1000;
  int HOURS_IN_DAY = 24;
  int TICKS_IN_DAY = TICKS_IN_HOUR * HOURS_IN_DAY;
  /* This needs to be a float, otherwise there are ~62 minutes per hour */
  float TICKS_IN_MINUTE = TICKS_IN_HOUR / 60f;

  /* Total Calculation Methods */

  default long getTotalHours() {
    return getTotalHours(getTicks());
  }

  static long getTotalHours(long time) {
    return time / TICKS_IN_HOUR;
  }

  /**
   * Gets the current time, according to the specific calendar's offset and settings
   *
   * @return a time
   */
  long getTicks();

  default long getTotalDays() {
    return getTotalDays(getTicks());
  }

  static long getTotalDays(long time) {
    return time / TICKS_IN_DAY;
  }
}
