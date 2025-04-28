package su.terrafirmagreg.api.base.object.inventory.spi.container;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public abstract class BaseContainerItemStack extends BaseContainer {

  protected final ItemStack stack;
  protected final EntityPlayer player;
  protected int itemIndex;
  protected int itemDragIndex;
  protected boolean isOffhand;

  protected BaseContainerItemStack(InventoryPlayer playerInv, ItemStack stack) {
    this.player = playerInv.player;
    this.stack = stack;
    this.itemDragIndex = playerInv.currentItem;

    if (stack == player.getHeldItemMainhand()) {
      this.itemIndex = playerInv.currentItem + 27; // Mainhand opened inventory
      this.isOffhand = false;
    } else {
      this.itemIndex = -100; // Offhand, so ignore this rule
      this.isOffhand = true;
    }

    addContainerSlots();
    addPlayerInventorySlots(playerInv);
  }

  protected abstract void addContainerSlots();

  protected void addPlayerInventorySlots(InventoryPlayer playerInv) {
    // Add Player Inventory Slots
    for (int stackSlotY = 0; stackSlotY < 3; stackSlotY++) {
      for (int stackSlotX = 0; stackSlotX < 9; stackSlotX++) {
        addSlotToContainer(new Slot(playerInv, stackSlotX + stackSlotY * 9 + 9, 8 + stackSlotX * 18, 84 + stackSlotY * 18));
      }
    }

    for (int hotbar = 0; hotbar < 9; hotbar++) {
      addSlotToContainer(new Slot(playerInv, hotbar, 8 + hotbar * 18, 142));
    }
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer player, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    // Slot that was clicked
    Slot slot = inventorySlots.get(index);

    if (slot == null || !slot.getHasStack()) {
      return itemstack;
    }

    if (index == itemIndex) {
      return itemstack;
    }

    ItemStack stack = slot.getStack();
    itemstack = stack.copy();

    // Begin custom transfer code here
    int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size(); // number of slots in the container
    if (index < containerSlots) {
      // Transfer out of the container
      if (!this.mergeItemStack(stack, containerSlots, inventorySlots.size(), true)) {
        // Don't transfer anything
        return ItemStack.EMPTY;
      }
    }
    // Transfer into the container
    else {
      if (!this.mergeItemStack(stack, 0, containerSlots, false)) {
        return ItemStack.EMPTY;
      }
    }

    if (stack.getCount() == 0) {
      slot.putStack(ItemStack.EMPTY);
    } else {
      slot.onSlotChanged();
    }
    if (stack.getCount() == itemstack.getCount()) {
      return ItemStack.EMPTY;
    }
    slot.onTake(player, stack);
    return itemstack;
  }

  @Override
  @NotNull
  public ItemStack slotClick(int slotID, int dragType, ClickType clickType, EntityPlayer player) {
    // Prevent moving of the item stack that is currently open
    if (slotID == itemIndex &&
        (clickType == ClickType.QUICK_MOVE || clickType == ClickType.PICKUP
         || clickType == ClickType.THROW || clickType == ClickType.SWAP)) {
      return ItemStack.EMPTY;
    } else if ((dragType == itemDragIndex) && clickType == ClickType.SWAP) {
      return ItemStack.EMPTY;
    } else {
      return super.slotClick(slotID, dragType, clickType, player);
    }
  }

}
