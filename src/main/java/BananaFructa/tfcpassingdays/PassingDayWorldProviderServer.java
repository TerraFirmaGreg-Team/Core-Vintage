package BananaFructa.tfcpassingdays;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.MinecraftForge;


import net.dries007.tfc.util.calendar.Calendar;

public class PassingDayWorldProviderServer extends PassingDaysWorldProvider {

    boolean interceptCalls = false;
    int callsZ = 0;

    public PassingDayWorldProviderServer(WorldProvider provider) {
        super(provider);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void shouldInterceptCalls(boolean f) {
        interceptCalls = f;
    }

    public void setZIntercept(int z) {
        callsZ = z;
    }

    @Override
    public boolean isDaytime() {
        if (interceptCalls) return isDaytime(callsZ);
        else return super.isDaytime();
    }

    public boolean isDaytime(int z) {
        return getSubtractedSkylight(z) < 4;
    }

    public int getSubtractedSkylight(int z) {
        return calculateSkylightSubtracted(1, z);
    }

    public float getCelestialAngleRadians(float partialTicks, int z) {
        float f = this.calculateCelestialAngleAtPos(world.getWorldTime(), partialTicks, z);
        return f * ((float) Math.PI * 2F);
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        if (interceptCalls) return calculateCelestialAngleAtPos(worldTime, partialTicks, callsZ);
        else return super.calculateCelestialAngle(worldTime, partialTicks);
    }

    public int calculateSkylightSubtracted(float partialTicks, int z) {
        float f = getSunBrightnessFactor(partialTicks, z);
        f = 1 - f;
        return (int) (f * 11);
    }

    public float getSunBrightnessFactor(float partialTicks, int z) {
        float f = this.calculateCelestialAngleAtPos(world.getWorldTime(), partialTicks, z);
        float f1 = 1.0F - (MathHelper.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.5F);
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        f1 = 1.0F - f1;
        f1 = (float) ((double) f1 * (1.0D - (double) (world.getRainStrength(partialTicks) * 5.0F) / 16.0D));
        f1 = (float) ((double) f1 * (1.0D - (double) (world.getThunderStrength(partialTicks) * 5.0F) / 16.0D));
        return f1;
    }

    public float calculateCelestialAngleAtPos(long worldTime, float partialTicks, int z) {
        int i = (int) (worldTime % 24000L);
        double baseMonth = translateMonth(Calendar.CALENDAR_TIME.getMonthOfYear());
        baseMonth += (double) Calendar.CALENDAR_TIME.getDayOfMonth() / (double) Calendar.CALENDAR_TIME.getDaysInMonth();
        i = translateToLocalizedCelestialTime(i, getDayPrecentege(baseMonth, getWinterSolticeDayPrecentege(z)));
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
