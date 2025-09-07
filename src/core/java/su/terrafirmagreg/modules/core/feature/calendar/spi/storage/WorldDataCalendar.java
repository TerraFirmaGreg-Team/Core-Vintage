package su.terrafirmagreg.modules.core.feature.calendar.spi.storage;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.WorldUtils;
import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.modules.core.feature.calendar.spi.Calendar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;


public class WorldDataCalendar extends WorldSavedData {

  private static final String DATA_ID = ModUtils.localize("data", "calendar");
  private static final FrameworkLogger LOGGER = FrameworkLogger.of(DATA_ID);

  private static final String TAG_CALENDAR = "calendar";

  private final Calendar calendar;


  public WorldDataCalendar() {
    this(DATA_ID);
  }

  @SuppressWarnings("unused")
  public WorldDataCalendar(String dataId) {
    super(dataId);

    this.calendar = new Calendar();
  }

  @Nonnull
  public static WorldDataCalendar get(@Nonnull World world) {
    MapStorage mapStorage = world.getMapStorage();
    if (mapStorage == null) {
      throw new IllegalStateException("Unable to access calendar data - everything is wrong now");
    }

    WorldDataCalendar data = WorldUtils.getOrLoadData(mapStorage, WorldDataCalendar.class, DATA_ID);
    if (data == null) {
      // Unable to load data, so assign default values
      LOGGER.info("Creating default calendar world data.");
      data = new WorldDataCalendar();
      data.markDirty();
      mapStorage.setData(DATA_ID, data);
    }
    return data;
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {

    calendar.deserializeNBT(nbt.getCompoundTag(TAG_CALENDAR));
  }

  @Override
  @Nonnull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setTag(TAG_CALENDAR, Calendar.INSTANCE.serializeNBT());
    return nbt;
  }

  /**
   * Since this updates every tick, and doesn't store a local copy always assume it needs saving to disk
   */
  @Override
  public boolean isDirty() {
    return true;
  }

  @Nonnull
  public Calendar getCalendar() {
    return calendar;
  }
}
