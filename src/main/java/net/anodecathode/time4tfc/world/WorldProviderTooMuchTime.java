package net.anodecathode.time4tfc.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;


import net.anodecathode.time4tfc.data.SessionData;
import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;

import static net.anodecathode.time4tfc.data.SessionData.April;
import static net.anodecathode.time4tfc.data.SessionData.August;
import static net.anodecathode.time4tfc.data.SessionData.December;
import static net.anodecathode.time4tfc.data.SessionData.February;
import static net.anodecathode.time4tfc.data.SessionData.January;
import static net.anodecathode.time4tfc.data.SessionData.July;
import static net.anodecathode.time4tfc.data.SessionData.June;
import static net.anodecathode.time4tfc.data.SessionData.March;
import static net.anodecathode.time4tfc.data.SessionData.May;
import static net.anodecathode.time4tfc.data.SessionData.November;
import static net.anodecathode.time4tfc.data.SessionData.October;
import static net.anodecathode.time4tfc.data.SessionData.September;

/**
 * @author dmillerw, messed up by AnodeCathode
 */
public class WorldProviderTooMuchTime extends WorldProvider {

  public static final DimensionType OVERWORLD = DimensionType.register("overworld", "", 0, WorldProviderTooMuchTime.class, true);
  int[] tfcDayLength = {January, February, March, April, May, June, July, August, September, October, November, December};

  public static void overrideDefault() {
    DimensionManager.unregisterDimension(0);
    DimensionManager.registerDimension(0, OVERWORLD);

  }

  @Override
  public float calculateCelestialAngle(long time, float partial) {
    if (!SessionData.modEnabled) {
      return super.calculateCelestialAngle(time, partial);
    }

    if (SessionData.tfcSeasons) {
      int month = Calendar.CALENDAR_TIME.getMonthOfYear().ordinal();

      SessionData.dayDuration = tfcDayLength[month];
      SessionData.nightDuration = 24000 - SessionData.dayDuration;
    }

    int absoluteTime = (int) (Math.max(1, time) % (SessionData.dayDuration + SessionData.nightDuration));
    boolean day = absoluteTime >= 0 && absoluteTime < SessionData.dayDuration;
    int cycleTime = day ? (absoluteTime % SessionData.dayDuration) : (absoluteTime - SessionData.dayDuration);
    float value = 0.5F * ((float) cycleTime + partial) / (day ? (float) (SessionData.dayDuration) : (float) (SessionData.nightDuration));

    if (day) {
      value += 0.75F;
    } else {
      value += 0.25F;
    }

    return value;
  }

  @Override
  public @NotNull DimensionType getDimensionType() {
    return DimensionType.OVERWORLD;
  }
}
