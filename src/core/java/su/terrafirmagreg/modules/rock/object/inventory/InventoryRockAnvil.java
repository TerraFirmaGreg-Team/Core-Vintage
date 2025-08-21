package su.terrafirmagreg.modules.rock.object.inventory;

import su.terrafirmagreg.framework.manager.registry.base.inventory.api.slot.ISlotCallback;
import su.terrafirmagreg.framework.manager.registry.base.inventory.spi.ItemStackHandlerCallback;
import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.forge.ICapabilityForge;

import net.minecraft.item.ItemStack;

import static su.terrafirmagreg.modules.rock.object.tile.TileRockAnvil.SLOT_INPUT_1;
import static su.terrafirmagreg.modules.rock.object.tile.TileRockAnvil.SLOT_INPUT_2;

public class InventoryRockAnvil extends ItemStackHandlerCallback {

  public InventoryRockAnvil(ISlotCallback callback, int slots) {
    super(callback, slots);
  }

  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    ItemStack result = super.extractItem(slot, amount, simulate);
    if (slot == SLOT_INPUT_1 || slot == SLOT_INPUT_2) {
      ICapabilityForge cap = result.getCapability(CapabilityForgeable.CAPABILITY, null);
      if (cap != null && cap.getRecipeName() != null && (!cap.getSteps().hasWork() || cap.getWork() == 0)) {
        cap.reset();
      }

    }
    return result;
  }
}
