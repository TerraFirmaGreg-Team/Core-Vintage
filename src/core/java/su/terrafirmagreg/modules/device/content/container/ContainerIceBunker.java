package su.terrafirmagreg.modules.device.content.container;

import su.terrafirmagreg.framework.manager.content.base.inventory.spi.container.BaseContainerTile;
import su.terrafirmagreg.framework.manager.content.base.inventory.spi.slot.SlotCallback;
import su.terrafirmagreg.modules.device.content.tile.TileIceBunker;

import net.minecraft.entity.player.InventoryPlayer;

public class ContainerIceBunker extends BaseContainerTile<TileIceBunker> {

  public ContainerIceBunker(InventoryPlayer playerInv, TileIceBunker tile) {
    super(playerInv, tile);

  }

  @Override
  protected void addContainerSlots() {
    var inventory = tile.getInventory();
    //Container
    for (int i = 0; i < 2; ++i) {
      for (int j = 0; j < 2; ++j) {
        this.addSlotToContainer(new SlotCallback(inventory, j + i * 2, 8 + j * 18 + 63, 18 + i * 18 + 7, tile));
      }
    }
  }

}
