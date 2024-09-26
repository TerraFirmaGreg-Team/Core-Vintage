package net.dries007.tfc.compat.waila.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Interfaces waila behavior to TOP and Hwyla
 */
public interface IWailaBlock {

  /**
   * Returns a list of tooltips to write on the Hwyla or TOP panel's body.
   *
   * @param world world obj
   * @param pos   Block's pos
   * @param nbt   the server sync nbt (not always possible, but non null for checking)
   * @return a List containing tooltips to write on the panel's body
   */
  @NotNull
  default List<String> getTooltip(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
    return Collections.emptyList();
  }

  /**
   * Overrides the title (default to the name of the block looked upon)
   *
   * @param world world obj
   * @param pos   Block's pos
   * @param nbt   the server sync nbt (not always possible, but non null for checking)
   * @return a List containing tooltips to write on the panel's head
   */
  @NotNull
  default String getTitle(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
    return "";
  }

  /**
   * Overrides the ItemStack used for icon
   *
   * @param world world obj
   * @param pos   Block's pos
   * @param nbt   the server sync nbt (not always possible, but non null for checking)
   * @return a ItemStack to be shown at the side of the panel.
   */
  @NotNull
  default ItemStack getIcon(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
    return ItemStack.EMPTY;
  }

  /**
   * Returns a list of classes that Hwyla and TOP should assign to this provider
   *
   * @return List of classes (eg: <Block>.class, <TileEntity>.class)
   */
  @NotNull
  List<Class<?>> getLookupClass();

  /**
   * Overrides this to tell Hwyla and TOP to override the default title (eg: The name of the block you're looking at).
   *
   * @return true if you wish to override the name of the block you're looking at
   */
  default boolean overrideTitle() {
    return false;
  }

  /**
   * Allows getTooltip to be queried and appended to the body of the Hwyla tooltip
   *
   * @return true if you wish to append to the Hwyla tooltip
   */
  default boolean appendBody() {
    return true;
  }

  /**
   * Overrides this to tell Hwyla and TOP to override the default stack (eg: The icon that is shown when you're looking at something).
   *
   * @return true if you wish to override the stack icon of the block you're looking at
   */
  default boolean overrideIcon() {
    return false;
  }
}
