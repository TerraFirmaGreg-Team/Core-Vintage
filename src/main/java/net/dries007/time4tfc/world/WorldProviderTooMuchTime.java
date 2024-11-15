package net.dries007.time4tfc.world;

import su.terrafirmagreg.api.library.MCDate;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

import net.dries007.time4tfc.time4tfc;

import org.jetbrains.annotations.NotNull;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import static net.dries007.time4tfc.network.packet.PacketServerSettings.dayDuration;
import static net.dries007.time4tfc.network.packet.PacketServerSettings.nightDuration;

/**
 * @author dmillerw, messed up by AnodeCathode
 */
public class WorldProviderTooMuchTime extends WorldProvider {

  public static final DimensionType OVERWORLD = DimensionType.register("overworld", "", 0, WorldProviderTooMuchTime.class, true);


  public static void overrideDefault() {
    DimensionManager.unregisterDimension(0);
    DimensionManager.registerDimension(0, OVERWORLD);

  }

  @Override
  public float calculateCelestialAngle(long time, float partial) {
    if (!time4tfc.modEnabled) {
      return super.calculateCelestialAngle(time, partial);
    }

    dayDuration = Calendar.CALENDAR_TIME.getMonthOfYear().getDaylightlength();
    nightDuration = (int) (MCDate.TICKS_IN_DAY - dayDuration);

    int absoluteTime = (int) (Math.max(1, time) % (dayDuration + nightDuration));
    boolean day = absoluteTime >= 0 && absoluteTime < dayDuration;
    int cycleTime = day ? (absoluteTime % dayDuration) : (absoluteTime - dayDuration);
    float value = 0.5F * ((float) cycleTime + partial) / (day ? (float) (dayDuration) : (float) (nightDuration));

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
