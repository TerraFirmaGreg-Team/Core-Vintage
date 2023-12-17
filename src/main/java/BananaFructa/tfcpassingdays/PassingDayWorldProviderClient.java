package BananaFructa.tfcpassingdays;

import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldProvider;

public class PassingDayWorldProviderClient extends PassingDaysWorldProvider {


    public PassingDayWorldProviderClient(WorldProvider provider) {
        super(provider);
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        int i = (int) (worldTime % 24000L);
        double baseMonth = translateMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear());
        baseMonth += (double) CalendarTFC.CALENDAR_TIME.getDayOfMonth() / (double) CalendarTFC.CALENDAR_TIME.getDaysInMonth();
        i = translateToLocalizedCelestialTime(i, getDayPrecentege(baseMonth, getWinterSolticeDayPrecentege(Minecraft.getMinecraft().player.posZ)));
        float f = ((float) i + partialTicks) / 24000.0F - 0.25F;
        if (f < 0.0F) {
            ++f;
        }

        if (f > 1.0F) {
            --f;
        }

        float f1 = 1.0F - (float) ((Math.cos((double) f * Math.PI) + 1.0D) / 2.0D);
        f = f + (f1 - f) / 3.0F;
        return f;
    }


}
