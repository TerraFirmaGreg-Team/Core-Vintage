package net.dries007.tfc.util.calendar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;


import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;


public class CalendarWorldData extends WorldSavedData {
	private static final String NAME = MODID_TFC + "_calendar";
	private static final String NAME_PRE_V1_5_0_X = MODID_TFC + ":calendar";

	private static final Logger LOGGER = LogManager.getLogger();
	private final CalendarTFC calendar;

	@SuppressWarnings("unused")
	public CalendarWorldData(String name) {
		super(name);
		this.calendar = new CalendarTFC();
	}

	@NotNull
	public static CalendarWorldData get(@NotNull World world) {
		MapStorage mapStorage = world.getMapStorage();
		if (mapStorage != null) {
			CalendarWorldData data = (CalendarWorldData) mapStorage.getOrLoadData(CalendarWorldData.class, NAME);
			if (data == null) {
				// Unable to load data, so assign default values
				LOGGER.info("Creating default calendar world data.");
				data = new CalendarWorldData(NAME);
				data.markDirty();
				mapStorage.setData(NAME, data);

				// If present, try and load old data
				CalendarWorldData oldData = (CalendarWorldData) mapStorage.getOrLoadData(CalendarWorldData.class, NAME_PRE_V1_5_0_X);
				if (oldData != null) {
					// Old data found. Copy it to the new location.
					// We don't use WorldSavedData write / read methods as it will just write the current state of the calendar (which is not yet loaded)
					LOGGER.info("Found v1.5.x save data, migrating it to new location.");
					data.calendar.deserializeNBT(oldData.calendar.serializeNBT());
				}
			}
			return data;
		}
		throw new IllegalStateException("Unable to access calendar data - everything is wrong now");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		calendar.deserializeNBT(nbt.getCompoundTag("calendar"));
	}

	@Override
	@NotNull
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("calendar", CalendarTFC.INSTANCE.serializeNBT());
		return nbt;
	}

	/**
	 * Since this updates every tick, and doesn't store a local copy always assume it needs saving to disk
	 */
	@Override
	public boolean isDirty() {
		return true;
	}

	@NotNull
	public CalendarTFC getCalendar() {
		return calendar;
	}
}
