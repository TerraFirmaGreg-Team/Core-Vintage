package su.terrafirmagreg.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

  //    @NotNull
  //    @Override
  //    public String getMonthName(Month month, boolean useSeasons) {
  //        return I18n.format(useSeasons ? "tfc.enum.season." + month.name().toLowerCase() : Helpers.getEnumName(month));
  //    }
  //
  //    @NotNull
  //    @Override
  //    public String getDayName(int dayOfMonth, long totalDays) {
  //        String date = CalendarTFC.CALENDAR_TIME.getMonthOfYear().name() + dayOfMonth;
  //        String birthday = CalendarTFC.BIRTHDAYS.get(date);
  //        if (birthday != null) {
  //            return birthday;
  //        }
  //        return I18n.format("tfc.enum.day." + CalendarTFC.DAY_NAMES[(int) (totalDays % 7)]);
  //    }
  //
  //    @NotNull
  //    @Override
  //    public String getDate(int hour, int minute, String monthName, int day, long years) {
  //        // We call an additional String.format for the time, because vanilla doesn't support %02d format specifiers
  //        return I18n.format("tfc.tooltip.calendar_full_date", String.format("%02d:%02d", hour, minute), monthName, day, years);
  //    }
}
