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
    return this.callback.getSlotLimit(slot);
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    return this.callback.isItemValid(slot, stack);
  }

  @Override
  protected void onContentsChanged(int slot) {
    this.callback.setAndUpdateSlots(slot);
  }
}
