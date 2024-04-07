package net.dries007.tfc.objects.te;

import net.minecraft.nbt.NBTTagCompound;

import net.dries007.tfc.util.calendar.CalendarTFC;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("tick", lastUpdateTick);
        return super.writeToNBT(nbt);
    }
}
