package su.terrafirmagreg.modules.animal.feature.egg.capability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Capability for egg item Allows egg to be fertilized, how long till hatching and also which entity will be born
 */
public interface ICapabilityEgg extends ICapabilitySerializable<NBTTagCompound> {

  /**
   * return the entity this egg will hatch to
   *
   * @return the Entity that is hatched from this egg, or null if none
   */
  @Nullable
  Entity getEntity(World world);

  /**
   * Fertilizes this egg, setting what entity and which day this egg will hatch
   *
   * @param entity   the entity this egg's gonna hatch
   * @param hatchDay the hatch day, as in CalendarTFC#getTotalDays
   */
  void setFertilized(@NotNull Entity entity, long hatchDay);

  /**
   * Is this egg fertilized?
   *
   * @return true if this egg is fertilized.
   */
  boolean isFertilized();

  /**
   * returns the day which this egg will hatch into the entity
   *
   * @return the day value, as in CalendarTFC#getTotalDays
   */
  long getHatchDay();
}
