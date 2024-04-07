package su.terrafirmagreg.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IProxy {

    @NotNull
    IThreadListener getThreadListener(MessageContext context);

    @Nullable
    EntityPlayer getPlayer(MessageContext context);

    @Nullable
    World getWorld(MessageContext context);

    // Calendar Translation / Localization Methods

    //    @NotNull
    //    String getMonthName(Month month, boolean useSeasons);
    //
    //    @NotNull
    //    String getDayName(int dayOfMonth, long totalDays);
    //
    //    @NotNull
    //    String getDate(int hour, int minute, String monthName, int day, long years);
    //
    class WrongSideException extends RuntimeException {

        WrongSideException(String message) {
            super(message);
        }
    }
}
