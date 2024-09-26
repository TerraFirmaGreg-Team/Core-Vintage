package su.terrafirmagreg.modules.rock.object.inventory;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.inventory.ISlotCallback;
import net.dries007.tfc.api.capability.inventory.ItemStackHandlerCallback;

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
      IForgeable cap = result.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
      if (cap != null && cap.getRecipeName() != null && (!cap.getSteps().hasWork() || cap.getWork() == 0)) {
        cap.reset();
      }

    }
    return result;
  }
}
