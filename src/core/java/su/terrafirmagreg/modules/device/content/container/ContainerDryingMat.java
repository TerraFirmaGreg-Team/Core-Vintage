package su.terrafirmagreg.modules.device.content.container;

import su.terrafirmagreg.framework.manager.content.base.inventory.spi.container.BaseContainerTile;
import su.terrafirmagreg.framework.manager.content.base.inventory.spi.slot.SlotCallback;
import su.terrafirmagreg.modules.device.content.tile.TileDryingMat;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerDryingMat extends BaseContainerTile<TileDryingMat> {

  public ContainerDryingMat(InventoryPlayer playerInv, TileDryingMat te) {
    super(playerInv, te);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    if (inventory != null) {
      addSlotToContainer(new SlotCallback(inventory, TileDryingMat.SLOT, 80, 34, tile));
    }
  }

}
