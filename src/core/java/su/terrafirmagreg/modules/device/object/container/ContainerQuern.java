package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.modules.device.object.tile.TileQuern;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.objects.container.ContainerTE;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import static su.terrafirmagreg.modules.device.object.tile.TileQuern.SLOT_HANDSTONE;
import static su.terrafirmagreg.modules.device.object.tile.TileQuern.SLOT_INPUT;
import static su.terrafirmagreg.modules.device.object.tile.TileQuern.SLOT_OUTPUT;

public class ContainerQuern extends ContainerTE<TileQuern> {

  public ContainerQuern(InventoryPlayer playerInv, TileQuern te) {
    super(playerInv, te);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    if (inventory != null) {
      addSlotToContainer(new SlotCallback(inventory, SLOT_HANDSTONE, 93, 20, tile));
      addSlotToContainer(new SlotCallback(inventory, SLOT_INPUT, 66, 47, tile));
      addSlotToContainer(new SlotCallback(inventory, SLOT_OUTPUT, 93, 47, tile));
    }
  }
}
