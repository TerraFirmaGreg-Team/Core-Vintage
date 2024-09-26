package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.api.base.container.BaseContainerTile;
import su.terrafirmagreg.modules.device.object.tile.TileCellarShelf;

import net.minecraft.entity.player.InventoryPlayer;

import net.dries007.tfc.objects.inventory.slot.SlotCallback;

public class ContainerCellarShelf extends BaseContainerTile<TileCellarShelf> {

  public ContainerCellarShelf(InventoryPlayer playerInv, TileCellarShelf tile) {
    super(playerInv, tile);
  }

  @Override
  protected void addContainerSlots() {
    for (int slotY = 0; slotY < 2; slotY++) {
      for (int slotX = 0; slotX < 7; slotX++) {

        addSlotToContainer(new SlotCallback(tile.getInventory(), slotX + slotY * 7, slotX * 18 + 26, slotY * 18 + 25, tile));
      }
    }
  }
}
