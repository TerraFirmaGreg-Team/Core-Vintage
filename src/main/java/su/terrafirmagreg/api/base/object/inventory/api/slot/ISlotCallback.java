package su.terrafirmagreg.api.base.object.inventory.api.slot;

import su.terrafirmagreg.api.base.object.inventory.spi.ItemStackHandlerCallback;
import su.terrafirmagreg.api.base.object.inventory.spi.slot.SlotCallback;

import net.minecraft.item.ItemStack;

/**
 * This is a callback for various methods on an ItemStackHandler. Methods are default to support overriding as many or as little as nessecary
 * <p>
 * {@link ItemStackHandlerCallback}
 */
public interface ISlotCallback {

  /**
   * Gets the slot stack size
   *
   * @param slot the slot index
   */
  default int getSlotLimit(int slot) {
    return 64;
  }

  /**
   * Checks if an item is valid for a slot
   *
   * @param slot  the slot index
   * @param stack the stack to be inserted
   * @return true if the item can be inserted
   */
  default boolean isItemValid(int slot, ItemStack stack) {
    return true;
  }

  /**
   * Called when a slot changed
   *
   * @param slot the slot index, or -1 if the call method had no specific slot
   */
  default void setAndUpdateSlots(int slot) {
  }

  default void setAndUpdateSlots() {}

  default void beforePutStack(SlotCallback slot, ItemStack stack) {
  }
}
