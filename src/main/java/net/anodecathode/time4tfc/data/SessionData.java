package net.anodecathode.time4tfc.data;

import net.minecraftforge.common.config.Configuration;


import io.netty.buffer.ByteBuf;
import net.anodecathode.time4tfc.time4tfc;

/**
 * @author Anodecathode, dmillerw
 */
public class SessionData {

  public static boolean modEnabled = true;

  public static int dayDuration;
  public static int nightDuration;

  public static boolean staticMoon;

  public static int staticAngle;

  public static boolean tfcSeasons;
  public static int January;
  public static int February;
  public static int March;
  public static int April;
  public static int May;
  public static int June;
  public static int July;
  public static int August;
  public static int September;
  public static int October;
  public static int November;
  public static int December;

  public static void writeToBuffer(ByteBuf buffer) {
    buffer.writeInt(dayDuration);
    buffer.writeInt(nightDuration);
    buffer.writeBoolean(staticMoon);
    buffer.writeInt(staticAngle);
    buffer.writeBoolean(tfcSeasons);
  }

  public static void readFromBuffer(ByteBuf buffer) {
    dayDuration = buffer.readInt();
    nightDuration = buffer.readInt();
    staticMoon = buffer.readBoolean();
    staticAngle = buffer.readInt();
    tfcSeasons = buffer.readBoolean();
  }

  public static void loadFromConfiguration(Configuration configuration) {
    modEnabled = true;
    dayDuration = configuration.get("general", "dayDuration", 12000, "Constant duration for each Minecraft day")
            .getInt();
    nightDuration = configuration.get("general", "nightDuration", 12000, "Constant duration for each Minecraft night")
            .getInt();
    staticMoon = configuration.get("general", "staticMoon", false,
                    "Whether the moon should be the one affected by staticAngle. Setting this to false will make the sun be static instead")
            .getBoolean();
    staticAngle = configuration.get("general", "staticAngle", -1,
                    "Statically sets the sun/moon to a specific angle, can be used for infinite day/night. Set to -1 to disable")
            .getInt();
    tfcSeasons = configuration.get("general", "tfcSeasons", true, "Follow the TFC Seasons. True will adjust the day/night cycle for each month.")
            .getBoolean();
    January = Math.min(
            configuration.get("MonthLength", "01.January,", 10800, "Daylight length in ticks for January. Cannot be greater than 18000")
                    .getInt(), 18000);
    February = Math.min(
            configuration.get("MonthLength", "02.February,", 12000, "Daylight length in ticks for February. Cannot be greater than 18000")
                    .getInt(), 18000);
    March = Math.min(configuration.get("MonthLength", "03.March,", 13200, "Daylight length in ticks for March. Cannot be greater than 18000")
            .getInt(), 18000);
    April = Math.min(configuration.get("MonthLength", "04.April,", 14400, "Daylight length in ticks for April. Cannot be greater than 18000")
            .getInt(), 18000);
    May = Math.min(configuration.get("MonthLength", "05.May,", 15600, "Daylight length in ticks for May. Cannot be greater than 18000")
            .getInt(), 18000);
    June = Math.min(configuration.get("MonthLength", "06.June,", 16800, "Daylight length in ticks for June. Cannot be greater than 18000")
            .getInt(), 18000);
    July = Math.min(configuration.get("MonthLength", "07.July,", 18000, "Daylight length in ticks for July. Cannot be greater than 18000")
            .getInt(), 18000);
    August = Math.min(configuration.get("MonthLength", "08.August,", 16800, "Daylight length in ticks for August. Cannot be greater than 18000")
            .getInt(), 18000);
    September = Math.min(
            configuration.get("MonthLength", "09.September,", 15600, "Daylight length in ticks for September. Cannot be greater than 18000")
                    .getInt(), 18000);
    October = Math.min(
            configuration.get("MonthLength", "10.October,", 14400, "Daylight length in ticks for October. Cannot be greater than 18000")
                    .getInt(), 18000);
    November = Math.min(
            configuration.get("MonthLength", "11.November,", 13200, "Daylight length in ticks for November. Cannot be greater than 18000")
                    .getInt(), 18000);
    December = Math.min(
            configuration.get("MonthLength", "12.December,", 12000, "Daylight length in ticks for December. Cannot be greater than 18000")
                    .getInt(), 18000);
    if (dayDuration <= 0) {
      dayDuration = 12000;
    }

    if (nightDuration <= 0) {
      nightDuration = 12000;
    }

    if (staticAngle < -1) {
      staticAngle = -1;
    } else if (staticAngle > 180) {
      staticAngle = 180;
    }

    if (configuration.hasChanged()) {
      configuration.save();
    }
  }

  public static void setConfigurationProperty(String category, String key, int value) {
    time4tfc.configuration.getCategory(category).get(key).setValue(value);
  }

  public static void setConfigurationProperty(String category, String key, boolean value) {
    time4tfc.configuration.getCategory(category).get(key).setValue(value);
  }
}
