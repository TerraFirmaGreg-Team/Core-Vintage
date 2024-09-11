package net.dries007.tfc.compat.waila.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;


import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interfaces waila behavior to TOP and Hwyla
 */
public interface IWailaEntity {

  /**
   * Returns a list of tooltips to write on the Hwyla or TOP panel's body.
   *
   * @param entity this entity
   * @param nbt    the server sync nbt (not always possible, but non null for checking)
   * @return a List containing tooltips to write on the panel's body
   */
  @NotNull
  List<String> getTooltip(@NotNull Entity entity, @NotNull NBTTagCompound nbt);

  /**
   * Overrides the title (default to the name of the entity looked upon)
   *
   * @param entity this entity
   * @param nbt    the server sync nbt (not always possible, but non null for checking)
   * @return the title
   */
  @NotNull
  default String getTitle(@NotNull Entity entity, @NotNull NBTTagCompound nbt) {
    return "";
  }

  /**
   * Returns a list of classes that Hwyla and TOP should assign to this provider
   *
   * @return List of classes (eg: <Entity>.class)
   */
  @NotNull
  List<Class<?>> getLookupClass();

  /**
   * Overrides this to tell Hwyla and TOP to override the default title (eg: The name of the entity you're looking at).
   *
   * @return true if you wish to override the name of the entity you're looking at
   */
  default boolean overrideTitle() {
    return false;
  }
}
