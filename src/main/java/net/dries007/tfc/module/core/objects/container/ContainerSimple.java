package net.dries007.tfc.module.core.objects.container;

import net.dries007.tfc.module.core.api.container.ContainerItemStack;
import net.dries007.tfc.module.core.api.container.ContainerTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A very simple container implementation.
 * Used for gui's that have no internal inventory, or no TE they need to access
 * Prefer using {@link ContainerTE} instead for tile entities or {@link ContainerItemStack} for items
 */
@ParametersAreNonnullByDefault
public class ContainerSimple extends Container {
    public ContainerSimple() {
    }

    public ContainerSimple(InventoryPlayer playerInv) {
        addPlayerInventorySlots(playerInv);
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stackCopy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            stackCopy = stack.copy();

            if (index < 27) {
                if (!this.mergeItemStack(stack, 27, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.mergeItemStack(stack, 0, 27, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == stackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack stackTake = slot.onTake(player, stack);
            if (index == 0) {
                player.dropItem(stackTake, false);
            }
        }

        return stackCopy;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    protected void addPlayerInventorySlots(InventoryPlayer playerInv) {
        addPlayerInventorySlots(playerInv, 0);
    }

    protected void addPlayerInventorySlots(InventoryPlayer playerInv, int yOffset) {
        // Add Player Inventory Slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + yOffset));
            }
        }

        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142 + yOffset));
        }
    }
}
