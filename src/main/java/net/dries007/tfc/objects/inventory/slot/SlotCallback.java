package net.dries007.tfc.objects.inventory.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;


import net.dries007.tfc.api.capability.inventory.ISlotCallback;

import org.jetbrains.annotations.NotNull;

public class SlotCallback extends SlotItemHandler {

    private final ISlotCallback callback;

    public SlotCallback(@NotNull IItemHandler inventory, int idx, int x, int y, @NotNull ISlotCallback callback) {
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
    public boolean isItemValid(@NotNull ItemStack stack) {
        return callback.isItemValid(getSlotIndex(), stack) && super.isItemValid(stack);
    }

    @Override
    public void putStack(@NotNull ItemStack stack) {
        callback.beforePutStack(this, stack);
        super.putStack(stack);
    }

    @Override
    public int getSlotStackLimit() {
        return Math.min(callback.getSlotLimit(getSlotIndex()), super.getSlotStackLimit());
    }
}
