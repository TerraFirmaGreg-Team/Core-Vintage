package su.terrafirmagreg.modules.core.object.command;

import su.terrafirmagreg.api.base.command.BaseCommand;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CommandTime extends BaseCommand {

  @Override
  public String getName() {
    return "time";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return "tfc.command.time.usage";
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args)
    throws CommandException {
    if (args.length >= 1) {
      if ("set".equals(args[0])) {
        if (args.length < 2) {
          throw new WrongUsageException("tfc.command.timetfc.usage_expected_second_argument_set");
        } else if ("monthlength".equals(args[1])) {
          int newMonthLength = parseInt(args[2], 1);
          Calendar.INSTANCE.setMonthLength(newMonthLength);
          notifyCommandListener(sender, this, "tfc.command.timetfc.set_month_length",
                                newMonthLength);
        } else {
          int resultWorldTime;
          if ("day".equals(args[1])) {
            resultWorldTime = 1000;
            notifyCommandListener(sender, this, "tfc.command.timetfc.set_day");
          } else if ("night".equals(args[1])) {
            resultWorldTime = 13000;
            notifyCommandListener(sender, this, "tfc.command.timetfc.set_night");
          } else {
            resultWorldTime = parseInt(args[1], 0);
            notifyCommandListener(sender, this, "tfc.command.timetfc.set_ticks", resultWorldTime);
          }
          setAllWorldTimes(server, resultWorldTime);
          Calendar.INSTANCE.setTimeFromWorldTime(resultWorldTime);
        }
      } else if ("add".equals(args[0])) {
        if (args.length < 2) {
          throw new WrongUsageException("tfc.command.timetfc.usage_expected_second_argument_add");
        }
        long timeToAdd;
        switch (args[1]) {
          case "months":
            int months = parseInt(args[2], 0);
            timeToAdd = ICalendar.TICKS_IN_DAY * Calendar.CALENDAR_TIME.getDaysInMonth() * months;
            notifyCommandListener(sender, this, "tfc.command.timetfc.add_months", months,
                                  timeToAdd);
            break;
          case "years":
            int years = parseInt(args[2], 0);
            timeToAdd =
              ICalendar.TICKS_IN_DAY * Calendar.CALENDAR_TIME.getDaysInMonth() * 12 * years;
            notifyCommandListener(sender, this, "tfc.command.timetfc.add_years", years, timeToAdd);
            break;
          case "days":
            int days = parseInt(args[2], 0);
            timeToAdd = (long) ICalendar.TICKS_IN_DAY * days;
            notifyCommandListener(sender, this, "tfc.command.timetfc.add_days", days, timeToAdd);
            break;
          default:
            timeToAdd = parseInt(args[1], 0);
            notifyCommandListener(sender, this, "tfc.command.timetfc.add_ticks", timeToAdd);
        }
        long newCalendarTime = Calendar.CALENDAR_TIME.getTicks() + timeToAdd;
        Calendar.INSTANCE.setTimeFromCalendarTime(newCalendarTime);
      } else if ("query".equals(args[0])) {
        if (args.length < 2) {
          throw new WrongUsageException("tfc.command.timetfc.usage_expected_second_argument_query");
        } else if ("daytime".equals(args[1])) {
          int daytime = (int) (sender.getEntityWorld().getWorldTime() % ICalendar.TICKS_IN_DAY);
          sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, daytime);
          notifyCommandListener(sender, this, "tfc.command.timetfc.query_daytime", daytime);
        } else if ("day".equals(args[1])) {
          int day = (int) (Calendar.CALENDAR_TIME.getTotalDays() % Integer.MAX_VALUE);
          sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, day);
          notifyCommandListener(sender, this, "tfc.command.timetfc.query_day", day);
        } else if ("gametime".equals(args[1])) {
          int gameTime = (int) (sender.getEntityWorld().getTotalWorldTime() % Integer.MAX_VALUE);
          sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gameTime);
          notifyCommandListener(sender, this, "tfc.command.timetfc.query_gametime", gameTime);
        } else if ("playerticks".equals(args[1])) {
          int gameTime = (int) (Calendar.PLAYER_TIME.getTicks() % Integer.MAX_VALUE);
          sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gameTime);
          notifyCommandListener(sender, this, "tfc.command.timetfc.query_playerticks", gameTime);
        } else if ("calendarticks".equals(args[1])) {
          int gameTime = (int) (Calendar.CALENDAR_TIME.getTicks() % Integer.MAX_VALUE);
          sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gameTime);
          notifyCommandListener(sender, this, "tfc.command.timetfc.query_calendarticks", gameTime);
        } else {
          throw new WrongUsageException("tfc.command.timetfc.usage_expected_second_argument_query");
        }
      } else {
        throw new WrongUsageException("tfc.command.timetfc.usage_expected_first_argument");
      }
    } else {
      throw new WrongUsageException("tfc.command.timetfc.usage_expected_first_argument");
    }
  }

  private void setAllWorldTimes(MinecraftServer server, long worldTime) {
    // Update the actual world times
    for (World world : server.worlds) {
      long worldTimeJump = worldTime - (world.getWorldTime() % ICalendar.TICKS_IN_DAY);
      if (worldTimeJump < 0) {
        worldTimeJump += ICalendar.TICKS_IN_DAY;
      }
      world.setWorldTime(world.getWorldTime() + worldTimeJump);
    }
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }

  @Override
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender,
                                        String[] args, @Nullable BlockPos targetPos) {
    if (args.length == 1) {
      return getListOfStringsMatchingLastWord(args, "set", "add", "query");
    } else if (args.length == 2) {
      if ("set".equals(args[0])) {
        return getListOfStringsMatchingLastWord(args, "day", "night", "monthlength");
      } else if ("add".equals(args[0])) {
        return getListOfStringsMatchingLastWord(args, "months", "years", "days");
      } else if ("query".equals(args[0])) {
        return getListOfStringsMatchingLastWord(args, "daytime", "day", "gametime", "playerticks",
                                                "calendarticks");
      }
    }
    return Collections.emptyList();
  }
}
