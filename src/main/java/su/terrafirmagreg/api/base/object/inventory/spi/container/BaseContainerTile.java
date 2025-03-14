package su.terrafirmagreg.api.base.object.inventory.spi.container;


import su.terrafirmagreg.api.base.object.tile.api.ITileFields;
import su.terrafirmagreg.api.base.object.tile.spi.BaseTileInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

/**
 * This is the mother of all Container-with-a-Tile-Entity implementations
 *
 * @param <T> The Tile Entity class
 */
@Getter
public abstract class BaseContainerTile<T extends BaseTileInventory> extends BaseContainer {

  protected final T tile;
  protected final EntityPlayer player;

  private final boolean shouldSyncFields;
  private final int yOffset; // The number of pixels higher than normal (If the gui is larger than normal, see Anvil)
  private int[] cachedFields;

  protected BaseContainerTile(InventoryPlayer playerInv, T tile) {
    this(playerInv, tile, 0);
  }

  protected BaseContainerTile(InventoryPlayer playerInv, T tile, int yOffset) {
    this.tile = tile;
    this.player = playerInv.player;
    this.shouldSyncFields = tile instanceof ITileFields;
    this.yOffset = yOffset;

    addContainerSlots();
    addPlayerInventorySlots(playerInv);
  }

  protected abstract void addContainerSlots();

  @Override
  protected void addPlayerInventorySlots(InventoryPlayer playerInv) {
    super.addPlayerInventorySlots(playerInv, yOffset);
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer player, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    // Slot that was clicked
    Slot slot = inventorySlots.get(index);
    if (slot != null && slot.getHasStack()) {
      ItemStack stack = slot.getStack();
      ItemStack stackCopy = stack.copy();

      // Transfer out of the container
      int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
      if (index < containerSlots) {
        if (transferStackOutOfContainer(stack, containerSlots)) {
          return ItemStack.EMPTY;
        }
      }
      // Transfer into the container
      else if (transferStackIntoContainer(stack, containerSlots)) {
        return ItemStack.EMPTY;
      }

      if (stack.getCount() == 0) {
        slot.putStack(ItemStack.EMPTY);
      } else {
        slot.onSlotChanged();
      }
      if (stack.getCount() == stackCopy.getCount()) {
        return ItemStack.EMPTY;
      }
      slot.onTake(player, stackCopy);
      return stackCopy;
    }
    return itemstack;
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {
    return tile.canInteractWith(player);
  }

  protected boolean transferStackOutOfContainer(ItemStack stack, int containerSlots) {
    return !mergeItemStack(stack, containerSlots, inventorySlots.size(), true);
  }

  protected boolean transferStackIntoContainer(ItemStack stack, int containerSlots) {
    return !mergeItemStack(stack, 0, containerSlots, false);
  }

  @Override
  public void detectAndSendChanges() {
    if (shouldSyncFields) {
      detectAndSendFieldChanges();
    }
    super.detectAndSendChanges();
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void updateProgressBar(int id, int data) {
    if (shouldSyncFields) {
      ((ITileFields) tile).setField(id, data);
    }
  }

  protected void detectAndSendFieldChanges() {
    ITileFields tileFields = (ITileFields) tile;
    boolean allFieldsHaveChanged = false;
    boolean[] fieldHasChanged = new boolean[tileFields.getFieldCount()];

    if (cachedFields == null) {
      cachedFields = new int[tileFields.getFieldCount()];
      allFieldsHaveChanged = true;
    }

    for (int i = 0; i < cachedFields.length; ++i) {
      if (allFieldsHaveChanged || cachedFields[i] != tileFields.getField(i)) {
        cachedFields[i] = tileFields.getField(i);
        fieldHasChanged[i] = true;
      }
    }

    // go through the list of listeners (players using this container) and update them if necessary
    for (IContainerListener listener : this.listeners) {
      for (int fieldID = 0; fieldID < tileFields.getFieldCount(); ++fieldID) {
        if (fieldHasChanged[fieldID]) {
          // Note that although sendWindowProperty takes 2 ints on a server these are truncated to shorts
          listener.sendWindowProperty(this, fieldID, cachedFields[fieldID]);
        }
      }
    }
  }
}
