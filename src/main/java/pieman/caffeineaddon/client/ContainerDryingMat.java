package pieman.caffeineaddon.client;

import su.terrafirmagreg.api.base.container.BaseContainerTile;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.objects.te.TEDryingMat;

public class ContainerDryingMat extends BaseContainerTile<TEDryingMat> {

  public ContainerDryingMat(InventoryPlayer playerInv, TEDryingMat te) {
    super(playerInv, te);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    if (inventory != null) {
      addSlotToContainer(new SlotCallback(inventory, TEDryingMat.SLOT, 80, 34, tile));
    }
  }

}
