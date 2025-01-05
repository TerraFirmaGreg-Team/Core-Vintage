package su.terrafirmagreg.api.base.object.inventory.spi;

import su.terrafirmagreg.api.base.object.inventory.api.slot.ISlotCallback;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerCallback extends ItemStackHandler {

  private final ISlotCallback callback;

  public ItemStackHandlerCallback(ISlotCallback callback, int slots) {
    super(slots);
    this.callback = callback;
  }

  @Override
  public int getSlotLimit(int slot) {
    return callback.getSlotLimit(slot);
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    return callback.isItemValid(slot, stack);
  }

  @Override
  protected void onContentsChanged(int slot) {
    callback.setAndUpdateSlots(slot);
  }
}
