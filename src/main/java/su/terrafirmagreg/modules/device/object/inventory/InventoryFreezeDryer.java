package su.terrafirmagreg.modules.device.object.inventory;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryFreezeDryer extends ItemStackHandler {

  public InventoryFreezeDryer(int size) {
    super(size);
    this.deserializeNBT(new NBTTagCompound());
  }

  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    ItemStack stack = super.extractItem(slot, amount, simulate);
    CapabilityFood.removeTrait(stack, FoodTrait.PRESERVING);
    return stack;
  }

}
