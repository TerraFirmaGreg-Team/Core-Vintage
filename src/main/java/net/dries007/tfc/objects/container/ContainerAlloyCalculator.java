package net.dries007.tfc.objects.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import net.dries007.tfc.objects.te.TEAlloyCalculator;

public class ContainerAlloyCalculator extends ContainerTE<TEAlloyCalculator> {


  public ContainerAlloyCalculator(InventoryPlayer playerInv, TEAlloyCalculator tile) {
    super(playerInv, tile, 19);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    for (int stackSlotY = 0; stackSlotY < 3; stackSlotY++) {
      for (int stackSlotX = 0; stackSlotX < 3; stackSlotX++) {
        int slot = stackSlotY * 3 + stackSlotX;
        this.addSlotToContainer(new SlotItemHandler(inventory, slot, 10 + stackSlotX * 18, 31 + stackSlotY * 18) {
          @Override
          public void onSlotChanged() {
            tile.calculateAlloy();
          }
        });
      }
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
      ItemStack stack = slot.getStack();
      itemstack = stack.copy();

      if (index < 9) {
        if (!this.mergeItemStack(stack, 9, this.inventorySlots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.mergeItemStack(stack, 0, 9, false)) {
        return ItemStack.EMPTY;
      }

      if (stack.isEmpty()) {
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
