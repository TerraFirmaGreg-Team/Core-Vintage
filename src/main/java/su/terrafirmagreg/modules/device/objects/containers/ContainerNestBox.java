package su.terrafirmagreg.modules.device.objects.containers;

import su.terrafirmagreg.api.base.container.BaseContainerTile;
import su.terrafirmagreg.modules.device.objects.tiles.TileNestBox;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

@MethodsReturnNonnullByDefault
public class ContainerNestBox extends BaseContainerTile<TileNestBox> {

  public ContainerNestBox(InventoryPlayer playerInv, TileNestBox tile) {
    super(playerInv, tile);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
            null);
    if (inventory != null) {
      addSlotToContainer(new SlotCallback(inventory, 0, 71, 23, tile));
      addSlotToContainer(new SlotCallback(inventory, 1, 89, 23, tile));
      addSlotToContainer(new SlotCallback(inventory, 2, 71, 41, tile));
      addSlotToContainer(new SlotCallback(inventory, 3, 89, 41, tile));
    }
  }
}
