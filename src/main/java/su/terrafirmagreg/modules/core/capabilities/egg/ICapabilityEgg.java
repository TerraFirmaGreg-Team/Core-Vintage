package su.terrafirmagreg.modules.core.capabilities.egg;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.client.ClientEvents;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
   * Tooltip added to the egg item Called from {@link ClientEvents}
   *
   * @param stack the stack in question
   * @param text  the tooltip
   */
  @SideOnly(Side.CLIENT)
  default void addTooltipInfo(ItemStack stack, List<String> text) {
    if (isFertilized()) {
      long remainingDays = this.getHatchDay() - Calendar.PLAYER_TIME.getTotalDays();
      text.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.fertilized"));
      if (remainingDays > 0) {
        text.add(I18n.format("tfc.tooltip.egg_hatch", remainingDays));
      } else {
        text.add(I18n.format("tfc.tooltip.egg_hatch_today"));
      }
    }
  }

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
