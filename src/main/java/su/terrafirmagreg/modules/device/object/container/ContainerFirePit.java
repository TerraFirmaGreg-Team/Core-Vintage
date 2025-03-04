package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.api.base.object.inventory.spi.container.BaseContainerTile;
import su.terrafirmagreg.api.base.object.inventory.spi.slot.SlotCallback;
import su.terrafirmagreg.api.data.enums.EnumFirePitAttachment;
import su.terrafirmagreg.modules.device.object.tile.TileFirePit;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import static su.terrafirmagreg.api.data.Properties.EnumProp.FIRE_PIT_ATTACHMENT;
import static su.terrafirmagreg.modules.device.object.tile.TileFirePit.SLOT_EXTRA_INPUT_END;
import static su.terrafirmagreg.modules.device.object.tile.TileFirePit.SLOT_EXTRA_INPUT_START;
import static su.terrafirmagreg.modules.device.object.tile.TileFirePit.SLOT_FUEL_INPUT;
import static su.terrafirmagreg.modules.device.object.tile.TileFirePit.SLOT_ITEM_INPUT;
import static su.terrafirmagreg.modules.device.object.tile.TileFirePit.SLOT_OUTPUT_1;
import static su.terrafirmagreg.modules.device.object.tile.TileFirePit.SLOT_OUTPUT_2;

public class ContainerFirePit extends BaseContainerTile<TileFirePit> {

  private final EnumFirePitAttachment attachment;

  public ContainerFirePit(InventoryPlayer playerInv, TileFirePit tile) {
    super(playerInv, tile);

    attachment = tile.getWorld().getBlockState(tile.getPos()).getValue(FIRE_PIT_ATTACHMENT);
  }

  @Override
  protected void addContainerSlots() {
    // Can't rely on the attachment variable because this is called in the super constructor, which happens before attachment is set
    EnumFirePitAttachment attachment = tile.getWorld()
      .getBlockState(tile.getPos())
      .getValue(FIRE_PIT_ATTACHMENT);
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    if (inventory != null) {
      // fuel slots
      for (int i = 0; i < 4; i++) {
        addSlotToContainer(new SlotCallback(inventory, i, 8, 62 - 18 * i, tile));
      }

      if (attachment == EnumFirePitAttachment.NONE) {
        // input slot
        addSlotToContainer(new SlotCallback(inventory, SLOT_ITEM_INPUT, 80, 20, tile));
        // output slots
        addSlotToContainer(new SlotCallback(inventory, SLOT_OUTPUT_1, 71, 48, tile));
        addSlotToContainer(new SlotCallback(inventory, SLOT_OUTPUT_2, 89, 48, tile));
      } else {
        // Both fire pit and cooking pot use these extra slots
        for (int i = SLOT_EXTRA_INPUT_START; i <= SLOT_EXTRA_INPUT_END; i++) {
          addSlotToContainer(
            new SlotCallback(inventory, i, 62 + (i - SLOT_EXTRA_INPUT_START) * 18, 20, tile));
        }
      }
    }
  }

  @Override
  protected boolean transferStackIntoContainer(ItemStack stack, int containerSlots) {
    switch (attachment) {
      case NONE:
        return !mergeItemStack(stack, SLOT_FUEL_INPUT, SLOT_ITEM_INPUT + 1, false);
      case COOKING_POT:
      case GRILL:
        return !mergeItemStack(stack, SLOT_FUEL_INPUT, SLOT_FUEL_INPUT + 1, false) &&
               !mergeItemStack(stack, SLOT_FUEL_INPUT + 1, SLOT_FUEL_INPUT + 6,
                 false); // this uses index of the slots sequentially, not the slot IDs themselves
    }
    return false;
  }
}
