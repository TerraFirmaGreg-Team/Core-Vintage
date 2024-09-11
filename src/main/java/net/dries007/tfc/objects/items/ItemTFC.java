package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemTFC extends Item implements ICapabilitySize {

  /**
   * This should NOT be overridden except for VERY SPECIAL cases If an item needs to not stack, i.e. small vessels, override
   * {@link ICapabilitySize#canStack(ItemStack)} If an item needs a variable stack size, override {@link ICapabilitySize#getWeight(ItemStack)} /
   * {@link ICapabilitySize#getSize(ItemStack)} and return a different value to get a different stack size
   */
  @Override
  public int getItemStackLimit(ItemStack stack) {
    return getStackSize(stack);
  }
}
