package su.terrafirmagreg.modules.device.objects.containers;

import su.terrafirmagreg.modules.device.objects.tiles.TileCharcoalForge;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.container.ContainerTE;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import static su.terrafirmagreg.modules.device.objects.tiles.TileCharcoalForge.SLOT_EXTRA_MAX;
import static su.terrafirmagreg.modules.device.objects.tiles.TileCharcoalForge.SLOT_EXTRA_MIN;
import static su.terrafirmagreg.modules.device.objects.tiles.TileCharcoalForge.SLOT_FUEL_MIN;
import static su.terrafirmagreg.modules.device.objects.tiles.TileCharcoalForge.SLOT_INPUT_MAX;
import static su.terrafirmagreg.modules.device.objects.tiles.TileCharcoalForge.SLOT_INPUT_MIN;

public class ContainerCharcoalForge extends ContainerTE<TileCharcoalForge> {

  public ContainerCharcoalForge(InventoryPlayer playerInv, TileCharcoalForge tile) {
    super(playerInv, tile);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
            null);
    if (inventory != null) {
      // Fuel slots
      // Note: the order of these statements is important
      int index = SLOT_FUEL_MIN;
      addSlotToContainer(new SlotCallback(inventory, index++, 80, 62, tile));
      addSlotToContainer(new SlotCallback(inventory, index++, 98, 44, tile));
      addSlotToContainer(new SlotCallback(inventory, index++, 62, 44, tile));
      addSlotToContainer(new SlotCallback(inventory, index++, 116, 26, tile));
      addSlotToContainer(new SlotCallback(inventory, index, 44, 26, tile));

      // Input slots
      // Note: the order of these statements is important
      index = SLOT_INPUT_MIN;
      addSlotToContainer(new SlotCallback(inventory, index++, 80, 44, tile));
      addSlotToContainer(new SlotCallback(inventory, index++, 98, 26, tile));
      addSlotToContainer(new SlotCallback(inventory, index++, 62, 26, tile));
      addSlotToContainer(new SlotCallback(inventory, index++, 116, 8, tile));
      addSlotToContainer(new SlotCallback(inventory, index, 44, 8, tile));

      // Extra slots (for ceramic molds)
      for (int i = SLOT_EXTRA_MIN; i <= SLOT_EXTRA_MAX; i++) {
        addSlotToContainer(
                new SlotCallback(inventory, i, 152, 8 + 18 * (i - SLOT_EXTRA_MIN), tile));
      }
    }
  }

  @Override
  protected boolean transferStackIntoContainer(ItemStack stack, int containerSlots) {
    return !mergeItemStack(stack, SLOT_EXTRA_MIN, SLOT_EXTRA_MAX + 1, false) && !mergeItemStack(
            stack, SLOT_FUEL_MIN, SLOT_INPUT_MAX + 1, false);
  }
}
