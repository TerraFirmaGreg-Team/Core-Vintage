package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.api.base.container.BaseContainerTile;
import su.terrafirmagreg.modules.device.object.tile.TileCrucible;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import static su.terrafirmagreg.modules.device.object.tile.TileCrucible.SLOT_INPUT_END;
import static su.terrafirmagreg.modules.device.object.tile.TileCrucible.SLOT_INPUT_START;
import static su.terrafirmagreg.modules.device.object.tile.TileCrucible.SLOT_OUTPUT;

public class ContainerCrucible extends BaseContainerTile<TileCrucible> {

  public ContainerCrucible(InventoryPlayer playerInv, TileCrucible tile) {
    super(playerInv, tile, 55);
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                                                null);
    if (inventory != null) {
      for (int i = SLOT_INPUT_START; i <= SLOT_INPUT_END; i++) {
        int line = i / 3;
        int column = i % 3;
        int x = 26 + column * 18;
        int y = 82 + line * 18;
        addSlotToContainer(new SlotCallback(inventory, i, x, y, tile));
      }

      addSlotToContainer(new SlotCallback(inventory, SLOT_OUTPUT, 152, 100, tile));
    }
  }
}
