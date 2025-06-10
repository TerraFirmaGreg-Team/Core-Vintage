package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.framework.manager.registry.base.inventory.spi.container.BaseContainerTile;
import su.terrafirmagreg.framework.manager.registry.base.inventory.spi.slot.SlotCallback;
import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.modules.device.object.tile.TileAlloyCalculator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;

public class ContainerAlloyCalculator extends BaseContainerTile<TileAlloyCalculator> {


  public ContainerAlloyCalculator(InventoryPlayer playerInv, TileAlloyCalculator tile) {
    super(playerInv, tile, 19);
  }

  @Override
  protected void addContainerSlots() {
    CapabilityUtils.getOptional(tile, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(cap -> {
      for (int stackSlotY = 0; stackSlotY < 3; stackSlotY++) {
        for (int stackSlotX = 0; stackSlotX < 3; stackSlotX++) {
          int slot = stackSlotY * 3 + stackSlotX;
          this.addSlotToContainer(new SlotCallback(cap, slot, 10 + stackSlotX * 18, 31 + stackSlotY * 18, tile));
        }
      }
    });


  }

}
