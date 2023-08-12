package net.dries007.tfc.common;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.util.WrongSideException;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = TerraFirmaCraft.MOD_ID)
public class CommonProxy {

    public void onPreInit() {

    }

    public void onInit() {

    }

    public void onPostInit() {

    }

    @Nonnull
    public IThreadListener getThreadListener(MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player.server;
        } else {
            throw new WrongSideException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
        }
    }

    @Nullable
    public EntityPlayer getPlayer(MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player;
        } else {
            throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
        }
    }

    @Nullable
    public World getWorld(MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player.getServerWorld();
        } else {
            throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
        }
    }

    @Nonnull
    public String getMonthName(Month month, boolean useSeasons) {
        return month.name().toLowerCase();
    }

    @Nonnull
    public String getDayName(int dayOfMonth, long totalDays) {
        return CalendarTFC.DAY_NAMES[(int) (totalDays % 7)];
    }

    @Nonnull
    public String getDate(int hour, int minute, String monthName, int day, long years) {
        return String.format("%02d:%02d %s %02d, %04d", hour, minute, monthName, day, years);
    }

}
