package su.terrafirmagreg.modules.device.objects.containers;

import su.terrafirmagreg.api.base.container.BaseContainerTile;
import su.terrafirmagreg.api.base.gui.component.button.IButtonHandler;
import su.terrafirmagreg.modules.device.objects.blocks.BlockCrate;
import su.terrafirmagreg.modules.device.objects.tiles.TileCrate;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import org.jetbrains.annotations.Nullable;

public class ContainerCrate extends BaseContainerTile<TileCrate> implements IButtonHandler {

  public ContainerCrate(InventoryPlayer playerInv, TileCrate tile) {
    super(playerInv, tile);
  }

  @Override
  public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
    // Slot will always be 0, extraNBT will be empty
    if (!tile.getWorld().isRemote) {
      BlockCrate.toggleCrateSeal(tile.getWorld(), tile.getPos());
    }
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
        null);

    if (inventory != null) {
      for (int y = 0; y < 3; y++) {
        for (int x = 0; x < 5; x++) {
          addSlotToContainer(
              new SlotCallback(inventory, x * 3 + y, 34 + x * 18, 19 + y * 18, tile));
        }
      }
    }
  }
}
