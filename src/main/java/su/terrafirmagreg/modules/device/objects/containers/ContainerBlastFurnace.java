package su.terrafirmagreg.modules.device.objects.containers;

import su.terrafirmagreg.modules.device.objects.tiles.TileBlastFurnace;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.container.ContainerTE;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import static su.terrafirmagreg.modules.device.objects.tiles.TileBlastFurnace.SLOT_TUYERE;

public class ContainerBlastFurnace extends ContainerTE<TileBlastFurnace> {

  public ContainerBlastFurnace(InventoryPlayer playerInv, TileBlastFurnace tile) {
    super(playerInv, tile);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
        null);
    if (inventory != null) {
      addSlotToContainer(new SlotCallback(inventory, SLOT_TUYERE, 153, 7, tile));
    }
  }
}
