package su.terrafirmagreg.modules.world.classic.objects.storage;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.LoggingHelper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public class WorldDataCalendar
  extends WorldSavedData {

  private static final String DATA_ID = ModUtils.localize("data.calendar");

  private static final LoggingHelper LOGGER = LoggingHelper.of(DATA_ID);

  private final Calendar calendar;

  private WorldDataCalendar() {
    super(DATA_ID);
    this.calendar = new Calendar();
  }

  @NotNull
  public static WorldDataCalendar get(@NotNull World world) {
    MapStorage mapStorage = world.getMapStorage();
    if (mapStorage != null) {
      var data = (WorldDataCalendar) mapStorage.getOrLoadData(WorldDataCalendar.class, DATA_ID);
      if (data == null) {
        // Unable to load data, so assign default values
        LOGGER.info("Creating default calendar world data.");
        data = new WorldDataCalendar();
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
