package su.terrafirmagreg.modules.device.objects.containers;

import su.terrafirmagreg.api.base.container.BaseContainerTile;
import su.terrafirmagreg.modules.device.objects.tiles.TileFreezeDryer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.inventory.slot.SlotCallback;

public class ContainerFreezeDryer extends BaseContainerTile<TileFreezeDryer> {

  public ContainerFreezeDryer(InventoryPlayer playerInv, TileFreezeDryer tile) {
    super(playerInv, tile);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
        null);

    if (inventory != null) {
      for (int y = 0; y < 3; y++) {
        for (int x = 0; x < 3; x++) {
          addSlotToContainer(new SlotCallback(inventory, x + y * 3, x * 18 + 8, y * 18 + 17, tile));
        }
      }
      addSlotToContainer(new SlotCallback(inventory, 9, 142, 17, tile));
    }
  }
}
