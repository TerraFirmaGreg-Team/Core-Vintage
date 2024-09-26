package net.dries007.tfc.proxy;

import su.terrafirmagreg.data.lib.MCDate;
import su.terrafirmagreg.data.lib.MCDate.Month;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {

  @NotNull
  @Override
  public IThreadListener getThreadListener(MessageContext context) {
    if (context.side.isClient()) {
      return Minecraft.getMinecraft();
    } else {
      return context.getServerHandler().player.server;
    }
  }

  @Override
  @Nullable
  public EntityPlayer getPlayer(MessageContext context) {
    if (context.side.isClient()) {
      return Minecraft.getMinecraft().player;
    } else {
      return context.getServerHandler().player;
    }
  }

  @Override
  @Nullable
  public World getWorld(MessageContext context) {
    if (context.side.isClient()) {
      return Minecraft.getMinecraft().world;
    } else {
      return context.getServerHandler().player.getEntityWorld();
    }
  }

  @NotNull
  @Override
  public String getMonthName(Month month, boolean useSeasons) {
    return I18n.format(useSeasons ? "tfc.enum.season." + month.name().toLowerCase() : Helpers.getEnumName(month));
  }

  @NotNull
  @Override
  public String getDayName(int dayOfMonth, long totalDays) {
    String date = Calendar.CALENDAR_TIME.getMonthOfYear().name() + dayOfMonth;
    String birthday = Calendar.BIRTHDAYS.get(date);
    if (birthday != null) {
      return birthday;
    }
    return I18n.format("tfc.enum.day." + MCDate.Week.valueOf((int) (totalDays % 7)).getName());
  }

  @NotNull
  @Override
  public String getDate(int hour, int minute, String monthName, int day, long years) {
    // We call an additional String.format for the time, because vanilla doesn't support %02d format specifiers
    return I18n.format("tfc.tooltip.calendar_full_date", String.format("%02d:%02d", hour, minute), monthName, day, years);
  }
}
