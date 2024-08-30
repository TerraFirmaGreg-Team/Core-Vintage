package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemTFCF extends Item implements ICapabilitySize {

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }
}
