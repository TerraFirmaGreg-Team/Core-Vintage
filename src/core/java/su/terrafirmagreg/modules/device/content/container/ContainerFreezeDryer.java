package su.terrafirmagreg.modules.device.content.container;

import su.terrafirmagreg.framework.manager.content.base.inventory.spi.container.BaseContainerTile;
import su.terrafirmagreg.framework.manager.content.base.inventory.spi.slot.SlotCallback;
import su.terrafirmagreg.modules.device.content.tile.TileFreezeDryer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerFreezeDryer extends BaseContainerTile<TileFreezeDryer> {

  public ContainerFreezeDryer(InventoryPlayer playerInv, TileFreezeDryer tile) {
    super(playerInv, tile);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

    if (inventory != null) {
      for (int stackSlotY = 0; stackSlotY < 3; stackSlotY++) {
        for (int stackSlotX = 0; stackSlotX < 3; stackSlotX++) {
          addSlotToContainer(new SlotCallback(inventory, stackSlotX + stackSlotY * 3, stackSlotX * 18 + 8, stackSlotY * 18 + 17, tile));
        }
      }
      addSlotToContainer(new SlotCallback(inventory, 9, 142, 17, tile));
    }
  }
}
