package su.terrafirmagreg.modules.core.feature.calendar;

import net.minecraftforge.common.config.Config;

public final class FeatureCalendarConfig {


  @Config.Comment({"Add Special Days!"})
  public final Birthday BIRTHDAYS = new Birthday();
  
  public final Month MONTH = new Month();

  public static class Month {

    public final January JANUARY = new January();

    public final February FEBRUARY = new February();

    public final March MARCH = new March();

    public final April APRIL = new April();

    public final May MAY = new May();

    public final June JUNE = new June();

    public final July JULY = new July();

    public final August AUGUST = new August();

    public final September SEPTEMBER = new September();

    public final October OCTOBER = new October();

    public final November NOVEMBER = new November();

    public final December DECEMBER = new December();

    @Config.Comment({
      "The default length of a month (in days) when a new world is started. " +
      "This can be changed in existing worlds via the /timetfc command."
    })
    @Config.RangeInt(min = 1, max = 31)
    public int defaultMonthLength = 8;

    @Config.Comment({"Constant duration for each Minecraft day"})
    @Config.RangeInt(min = 1)
    public int defaultDayDuration = 12000;

    @Config.Comment({"Constant duration for each Minecraft night"})
    @Config.RangeInt(min = 1)
    public int defaultNightDuration = 12000;

    @Config.Comment({"Whether the moon should be the one affected by staticAngle. Setting this to false will make the sun be static instead"})
    public boolean staticMoon = false;

    @Config.Comment({"Statically sets the sun/moon to a specific angle, can be used for infinite day/night. Set to -1 to disable"})
    @Config.RangeInt(min = -1, max = 180)
    public int staticAngle = -1;

    public static class January {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 31;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 10800;
    }

    public static class February {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 28;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 12000;
    }

    public static class March {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 31;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 13200;
    }

    public static class April {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 30;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 14400;
    }

    public static class May {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 31;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 15600;
    }

    public static class June {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 30;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 16800;
    }

    public static class July {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 31;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 18000;
    }

    public static class August {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 31;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 16800;
    }

    public static class September {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 30;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 15600;
    }

    public static class October {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 31;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 14400;
    }

    public static class November {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 30;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 13200;
    }

    public static class December {

      @Config.RangeInt(min = 1, max = 31)
      public int days = 31;

      @Config.RangeInt(min = 1, max = 18000)
      @Config.Comment({"Daylight length in ticks. Cannot be greater than 18000"})
      public int dayDuration = 12000;
    }

  }

  public static class Birthday {

    @Config.Comment({"Add special days to the TFC calendar!", "Format: MONTH<dayNumber> <name of your day>.",
                     "An invalid day string won't be read by the calendar. See defaults for examples."})
    @Config.RequiresMcRestart
    public String[] dayList = new String[]{
      "MARCH2 Firedale's Birthday"
    };
  }
}
