package su.terrafirmagreg.modules.world.api.data;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;


import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public class CalendarWorldData extends WorldSavedData {

    private static final String DATA_ID = ModUtils.localize("data.calendar");

    private static final LoggingHelper LOGGER = new LoggingHelper(DATA_ID);

    private final Calendar calendar;

    private CalendarWorldData() {
        super(DATA_ID);
        this.calendar = new Calendar();
    }

    @NotNull
    public static CalendarWorldData get(@NotNull World world) {
        MapStorage mapStorage = world.getMapStorage();
        if (mapStorage != null) {
            CalendarWorldData data = (CalendarWorldData) mapStorage.getOrLoadData(CalendarWorldData.class, DATA_ID);
            if (data == null) {
                // Unable to load data, so assign default values
                LOGGER.info("Creating default calendar world data.");
                data = new CalendarWorldData();
                data.markDirty();
                mapStorage.setData(DATA_ID, data);
            }
            return data;
        }
        throw new IllegalStateException("Unable to access calendar data!");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        calendar.deserializeNBT(nbt.getCompoundTag("calendar"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("calendar", Calendar.INSTANCE.serializeNBT());
        return nbt;
    }

    /**
     * Since this updates every tick, and doesn't store a local copy always assume it needs saving to disk
     */
    @Override
    public boolean isDirty() {
        return true;
    }

}
