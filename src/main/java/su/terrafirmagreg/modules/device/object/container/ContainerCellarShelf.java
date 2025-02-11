package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.modules.device.object.tile.TileCellarShelf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.objects.container.ContainerTE;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

public class ContainerCellarShelf extends ContainerTE<TileCellarShelf> {

  public ContainerCellarShelf(InventoryPlayer playerInv, TileCellarShelf tile, EntityPlayer player) {
    super(playerInv, tile);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

    if (inventory != null) {
      for (int y = 0; y < 2; y++) {
        for (int x = 0; x < 7; x++) {

          addSlotToContainer(new SlotCallback(inventory, x + y * 7, x * 18 + 26, y * 18 + 25, tile));
        }
      }
    }
  }
}
