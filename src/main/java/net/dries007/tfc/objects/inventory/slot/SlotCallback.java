package net.dries007.tfc.objects.inventory.slot;

import net.dries007.tfc.objects.inventory.capability.ISlotCallback;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotCallback extends SlotItemHandler {

  private final ISlotCallback callback;

  public SlotCallback(@Nonnull IItemHandler inventory, int idx, int x, int y, @Nonnull ISlotCallback callback) {
    super(inventory, idx, x, y);
    this.callback = callback;
  }

  @Override
  public void onSlotChanged() {
    // Calling this only happens here
    // If called in the container / item handler it can call during the middle of slot transfers, resulting in strange behavior
    callback.setAndUpdateSlots(getSlotIndex());
    super.onSlotChanged();
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack) {
    return callback.isItemValid(getSlotIndex(), stack) && super.isItemValid(stack);
  }

  @Override
  public void putStack(@Nonnull ItemStack stack) {
    callback.beforePutStack(this, stack);
    super.putStack(stack);
  }

  @Override
  public int getSlotStackLimit() {
    return Math.min(callback.getSlotLimit(getSlotIndex()), super.getSlotStackLimit());
  }
}
