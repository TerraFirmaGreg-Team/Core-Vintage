package su.terrafirmagreg.modules.core.capabilities.heat;

import su.terrafirmagreg.api.util.NBTUtils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This is an implementation of ItemHeat that automatically cools down over time Prefer extending or using this than implementing IItemHeat directly Exceptions
 * if you want to extend another capability object (see SmallVessel) but you should still implement this functionality somewhere
 */
public class ProviderHeat implements ICapabilityHeat {

  // These are "constants". Some implementations will want to change these based on other factors. (See ItemMold)
  protected float heatCapacity;
  protected float meltTemp;

  // These are the values from last point of update. They are updated when read from NBT, or when the temperature is set manually.
  // Note that if temperature is == 0, lastUpdateTick should set itself to -1 to keep their capabilities compatible - i.e. stackable
  protected float temperature;
  protected long lastUpdateTick;

  /**
   * Default ItemHeatHandler implementation
   *
   * @param nbt          The NBT of the itemstack. (Provided in Item#initCapabilities())
   * @param heatCapacity The heat capacity
   * @param meltTemp     The melting point
   */

  public ProviderHeat(@Nullable NBTTagCompound nbt, float heatCapacity, float meltTemp) {
    this.heatCapacity = heatCapacity;
    this.meltTemp = meltTemp;

    deserializeNBT(nbt);
  }

  public ProviderHeat(float heatCapacity, float meltTemp) {
    this.heatCapacity = heatCapacity;
    this.meltTemp = meltTemp;

    deserializeNBT(null);
  }

  public ProviderHeat() {
  } // This is here so you can do a custom implementation

  @Override
  @NotNull
  public NBTTagCompound serializeNBT() {
    NBTTagCompound nbt = new NBTTagCompound();
    if (getTemperature() <= 0) {
      // Reset temperature to zero
      NBTUtils.setGenericNBTValue(nbt, "ticks", -1);
      NBTUtils.setGenericNBTValue(nbt, "heat", 0);
    } else {
      // Serialize existing values - this is intentionally lazy (and not using the result of getTemperature())
      // Why? So we don't update the serialization unnecessarily. Important for not sending unnecessary client syncs.
      NBTUtils.setGenericNBTValue(nbt, "ticks", lastUpdateTick);
      NBTUtils.setGenericNBTValue(nbt, "heat", temperature);
    }
    return nbt;
  }

  @Override
  public void deserializeNBT(@Nullable NBTTagCompound nbt) {
    if (nbt != null) {
      temperature = nbt.getFloat("heat");
      lastUpdateTick = nbt.getLong("ticks");
    }
  }

  @Override
  public float getHeatCapacity() {
    return heatCapacity;
  }

  @Override
  public boolean isMolten() {
    return getTemperature() >= meltTemp;
  }

  /**
   * This gets the outwards facing temperature. It will differ from the internal temperature value or the value saved to NBT Note: if checking the temperature
   * internally, DO NOT use temperature, use this instead, as temperature does not represent the current temperature
   *
   * @return The current temperature
   */
  @Override
  public float getTemperature() {
    return CapabilityHeat.adjustTemp(temperature, heatCapacity,
                                     Calendar.PLAYER_TIME.getTicks() - lastUpdateTick);
  }

  /**
   * Update the temperature, and save the timestamp of when it was updated
   *
   * @param temperature the temperature to set. Between 0 - 1600
   */
  @Override
  public void setTemperature(float temperature) {
    this.temperature = temperature;
    this.lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
  }

  @Override
  public float getMeltTemp() {
    return meltTemp;
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityHeat.CAPABILITY;
  }

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) this : null;
  }


}
