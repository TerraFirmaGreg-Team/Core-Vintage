package dev.quarris.tfcalloycalc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCalc extends Container {

    public final TileEntityCalc tile;
    private final InventoryPlayer inv;
    private final IItemHandler stacks;

    public ContainerCalc(InventoryPlayer playerInv, TileEntityCalc tile) {
        this.tile = tile;
        this.stacks = tile.stacks;
        this.inv = playerInv;

        for (int stackSlotY = 0; stackSlotY < 3; stackSlotY++) {
            for (int stackSlotX = 0; stackSlotX < 3; stackSlotX++) {
                int slot = stackSlotY * 3 + stackSlotX;
                this.addSlotToContainer(new SlotItemHandler(this.stacks, slot, 10 + stackSlotX * 18, 31 + stackSlotY * 18) {
                    @Override
                    public void onSlotChanged() {
                        ContainerCalc.this.tile.calculateAlloy();
                    }
                });
            }
        }

        for (int slotY = 0; slotY < 3; ++slotY) {
            for (int slotX = 0; slotX < 9; ++slotX) {
                this.addSlotToContainer(new Slot(playerInv, slotX + slotY * 9 + 9, 8 + slotX * 18, 103 + slotY * 18));
            }
        }

        for (int hotbar = 0; hotbar < 9; ++hotbar) {
            this.addSlotToContainer(new Slot(playerInv, hotbar, 8 + hotbar * 18, 161));
        }
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return super.mergeItemStack(stack, startIndex, endIndex, reverseDirection);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 9) {
                if (!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
