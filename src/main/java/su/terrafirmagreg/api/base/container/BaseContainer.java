package su.terrafirmagreg.api.base.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * A very simple container implementation. Used for gui's that have no internal inventory, or no TE they need to access Prefer using {@link BaseContainerTile}
 * instead for tile entities or {@link BaseContainerItemStack} for items
 */
public abstract class BaseContainer extends Container {

  public BaseContainer() {
  }

  public BaseContainer(InventoryPlayer playerInv) {
    addPlayerInventorySlots(playerInv);

  }

  protected void addPlayerInventorySlots(InventoryPlayer playerInv) {
    addPlayerInventorySlots(playerInv, 0);
  }

  protected void addPlayerInventorySlots(InventoryPlayer playerInv, int yOffset) {
    // Add Player Inventory Slots
    for (int stackSlotY = 0; stackSlotY < 3; stackSlotY++) {
      for (int stackSlotX = 0; stackSlotX < 9; stackSlotX++) {
        addSlotToContainer(new Slot(playerInv, stackSlotX + stackSlotY * 9 + 9, 8 + stackSlotX * 18, 84 + stackSlotY * 18 + yOffset));
      }
    }

    for (int hotbar = 0; hotbar < 9; hotbar++) {
      addSlotToContainer(new Slot(playerInv, hotbar, 8 + hotbar * 18, 142 + yOffset));
    }
  }

  @Override
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
}
