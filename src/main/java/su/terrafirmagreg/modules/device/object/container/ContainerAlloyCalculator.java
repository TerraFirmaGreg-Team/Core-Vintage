package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.api.base.container.BaseContainerTile;
import su.terrafirmagreg.modules.device.object.tile.TileAlloyCalculator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.objects.inventory.slot.SlotCallback;

public class ContainerAlloyCalculator extends BaseContainerTile<TileAlloyCalculator> {


  public ContainerAlloyCalculator(InventoryPlayer playerInv, TileAlloyCalculator tile) {
    super(playerInv, tile, 19);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

    if (inventory != null) {
      for (int stackSlotY = 0; stackSlotY < 3; stackSlotY++) {
        for (int stackSlotX = 0; stackSlotX < 3; stackSlotX++) {
          int slot = stackSlotY * 3 + stackSlotX;
          this.addSlotToContainer(new SlotCallback(inventory, slot, 10 + stackSlotX * 18, 31 + stackSlotY * 18, tile));
        }
      }
    }

  }

}
