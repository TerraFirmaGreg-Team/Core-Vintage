package net.dries007.tfc.proxy;

import su.terrafirmagreg.api.exception.WrongSideException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import su.terrafirmagreg.modules.core.feature.calendar.Month;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("unused")
@SideOnly(Side.SERVER)
public class ServerProxy implements IProxy {

  @Override
  @Nonnull
  public IThreadListener getThreadListener(MessageContext context) {
    if (context.side.isServer()) {
      return context.getServerHandler().player.server;
    } else {
      throw new WrongSideException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
    }
  }

  @Override
  @Nullable
  public EntityPlayer getPlayer(MessageContext context) {
    if (context.side.isServer()) {
      return context.getServerHandler().player;
    } else {
      throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
    }
  }

  @Override
  @Nullable
  public World getWorld(MessageContext context) {
    if (context.side.isServer()) {
      return context.getServerHandler().player.getServerWorld();
    } else {
      throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
    }
  }

  @Nonnull
  @Override
  public String getMonthName(Month month, boolean useSeasons) {
    return month.name().toLowerCase();
  }

  @Nonnull
  @Override
  public String getDayName(int dayOfMonth, long totalDays) {
    return Calendar.DAY_NAMES[(int) (totalDays % 7)];
  }

  @Nonnull
  @Override
  public String getDate(int hour, int minute, String monthName, int day, long years) {
    return String.format("%02d:%02d %s %02d, %04d", hour, minute, monthName, day, years);
  }
}
