package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.modules.device.object.tile.TileIceBunker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerIceBunker extends Container {

  private final int numRows;
  private final TileIceBunker chestInventory;

  public ContainerIceBunker(InventoryPlayer playerInv, TileIceBunker chestInventory,
                            EntityPlayer player) {
    this.chestInventory = chestInventory;
    this.numRows = chestInventory.getSizeInventory() / 2;
    chestInventory.openInventory(player);

    //Container
    for (int i = 0; i < this.numRows; ++i) {
      for (int j = 0; j < 2; ++j) {
        this.addSlotToContainer(
          new Slot(chestInventory, j + i * 2, 8 + j * 18 + 63, 18 + i * 18 + 7));
      }
    }

    //Player Inventory
    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 9; x++) {
        this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }

    //HotBar
    for (int x = 0; x < 9; x++) {
      this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
    }

  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.inventorySlots.get(index);

    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();

      if (index < this.numRows * 9) {
        if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
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
  public void onContainerClosed(EntityPlayer entityPlayer) {
    super.onContainerClosed(entityPlayer);
    chestInventory.closeInventory(entityPlayer);
  }

  @Override
  public boolean canInteractWith(EntityPlayer entityPlayer) {
    return this.chestInventory.isUsableByPlayer(entityPlayer);
  }

  public TileIceBunker getChestInventory() {
    return this.chestInventory;
  }

}
