package su.terrafirmagreg.modules.device.object.inventory;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

import net.dries007.tfc.api.capability.food.CapabilityFood;

public class InventoryCellarShelf extends ItemStackHandler {

  public InventoryCellarShelf(int size) {
    super(size);
    this.deserializeNBT(new NBTTagCompound());
  }

  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    ItemStack stack = super.extractItem(slot, amount, simulate);

    NBTTagCompound nbt;
    if (stack.hasTagCompound()) {
      nbt = stack.getTagCompound();
    } else {
      nbt = new NBTTagCompound();
    }

    String string = nbt.getString("CellarAddonTemperature");
    if (string.compareTo("cool") == 0) {
      CapabilityFood.removeTrait(stack, FoodTrait.COOL);
    }
    if (string.compareTo("icy") == 0) {
      CapabilityFood.removeTrait(stack, FoodTrait.ICY);
    }
    if (string.compareTo("freezing") == 0) {
      CapabilityFood.removeTrait(stack, FoodTrait.FREEZING);
    }
    nbt.removeTag("CellarAddonTemperature");
    stack.setTagCompound(null);
    return stack;
  }

}
