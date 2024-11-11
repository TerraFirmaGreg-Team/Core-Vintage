package net.dries007.tfc.util.climate;

import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.modules.world.ConfigWorld;

import net.minecraft.util.math.MathHelper;

import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.ICalendarFormatted;
import net.dries007.tfc.util.config.TemperatureMode;

import static su.terrafirmagreg.api.util.MathUtils.RNG;
import static su.terrafirmagreg.modules.world.classic.WorldTypeClassic.SEALEVEL;

public class ClimateHelper {

  private ClimateHelper() {
  }

  /**
   * @return The month adjusted temperature. This gets the base temperature, before daily / hourly changes
   */
  public static float actualTemp(float regionalTemp, int y, int z, long timeOffset) {
    return dailyTemp(regionalTemp, z, timeOffset) - heightFactor(y);
  }

  /**
   * @return The exact temperature for a location, including day + hour variation, without height adjustment
   */
  public static float dailyTemp(float regionalTemp, int z, long timeOffset) {
    // Hottest part of the day at 12, coldest at 0
    int hourOfDay = ICalendarFormatted.getHourOfDay(Calendar.CALENDAR_TIME.getTicks() + timeOffset);
    if (hourOfDay > 12) {
      // Range: 0 - 12
      hourOfDay = 24 - hourOfDay;
    }
    // Range: -1 - 1
    float hourModifier = (hourOfDay / 6f) - 1f;

    // Note: this does not use world seed, as that is not synced from server - client, resulting in the seed being different
    long day = ICalendar.getTotalDays(Calendar.CALENDAR_TIME.getTicks() + timeOffset);
    RNG.setSeed(day);
    // Range: -1 - 1
    final float dailyModifier = RNG.nextFloat() - RNG.nextFloat();

    // Max daily / hourly variance is +/- 4 C
    return monthlyTemp(regionalTemp, z, timeOffset) + (dailyModifier + 0.3f * hourModifier) * 3f;
  }

  /**
   * Internationally accepted average lapse time is 6.49 K / 1000 m, for the first 11 km of the atmosphere. Our temperature is scales the 110 m against 2750 m,
   * so that gives us a change of 1.6225 / 10 blocks. The amount to reduce temperature by after applying height transformations
   *
   * @param y the y level
   * @return a value between 0 and 17.822
   */
  public static float heightFactor(int y) {
    if (y > SEALEVEL) {
      // This is much simpler and works just as well
      float scale = (y - SEALEVEL) * 0.16225f;
      if (scale > 17.822f) {
        scale = 17.822f;
      }
      return scale;
    }
    return 0;
  }

  /**
   * @return The month adjusted temperature. This gets the base temperature, before daily / hourly changes
   */
  public static float monthlyTemp(float regionalTemp, int z, long timeOffset) {
    long time = Calendar.CALENDAR_TIME.getTicks() + timeOffset;
    Month monthOfYear = ICalendarFormatted.getMonthOfYear(time, Calendar.CALENDAR_TIME.getDaysInMonth());

    final float currentMonthFactor = monthFactor(regionalTemp, monthOfYear, z);
    final float nextMonthFactor = monthFactor(regionalTemp, monthOfYear.next(), z);

    final float delta = (float) ICalendarFormatted.getDayOfMonth(time, Calendar.CALENDAR_TIME.getDaysInMonth()) /
                        Calendar.CALENDAR_TIME.getDaysInMonth();
    // Affine combination to smooth temperature transition
    return currentMonthFactor * (1 - delta) + nextMonthFactor * delta;
  }

  /**
   * Range -32 to 35
   *
   * @param regionalTemp The base temp for the current location
   * @param month        The month (from Calendar)
   * @return the month factor for temp calculation
   */
  public static float monthFactor(float regionalTemp, Month month, int z) {
    return monthFactor(regionalTemp, month.getTemperatureModifier(), z);
  }

  public static float monthFactor(float regionalTemp, float monthTempModifier, int z) {
    return (41f - monthTempModifier * 1.1f * (1 - 0.8f * latitudeFactor(z))) + regionalTemp;
  }

  /**
   * Range 0 - 1
   *
   * @param chunkZ the chunk Z position (in block coordinates)
   * @return the latitude factor for temperature calculation
   */
  public static float latitudeFactor(int chunkZ) {
    int tempRange = ConfigWorld.MISC.latitudeTemperatureModifier;
    if (ConfigWorld.MISC.temperatureMode == TemperatureMode.ENDLESS) {
      chunkZ = MathHelper.clamp(chunkZ, -tempRange / 2, tempRange / 2);
    }
    return 0.5f + 0.5f * ConfigWorld.MISC.hemisphereType.getValue() * (float) Math.sin(Math.PI * chunkZ / tempRange);
  }
}
