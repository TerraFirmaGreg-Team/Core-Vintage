package su.terrafirmagreg.modules.device.object.inventory;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import net.dries007.tfc.api.capability.food.CapabilityFood;

import org.jetbrains.annotations.NotNull;

public class InventoryLargeVessel extends ItemStackHandler {

  public InventoryLargeVessel(int slots) {
    super(slots);
  }

  @Override
  @NotNull
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    ItemStack stack = super.extractItem(slot, amount, simulate);
    CapabilityFood.removeTrait(stack, FoodTrait.PRESERVED);
    return stack;
  }
}
