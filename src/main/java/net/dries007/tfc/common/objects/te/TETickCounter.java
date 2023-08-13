package net.dries007.tfc.common.objects.te;

import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TETickCounter extends TEBase {
    private long lastUpdateTick;

    public long getTicksSinceUpdate() {
        return CalendarTFC.PLAYER_TIME.getTicks() - lastUpdateTick;
    }

    public void resetCounter() {
        lastUpdateTick = CalendarTFC.PLAYER_TIME.getTicks();
        markForSync();
    }

    public void reduceCounter(long amount) {
        lastUpdateTick += amount;
        markForSync();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        lastUpdateTick = nbt.getLong("tick");
        super.readFromNBT(nbt);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("tick", lastUpdateTick);
        return super.writeToNBT(nbt);
    }
}
