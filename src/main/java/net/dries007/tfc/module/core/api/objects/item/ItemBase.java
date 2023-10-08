package net.dries007.tfc.module.core.api.objects.item;

import net.dries007.tfc.module.core.api.capability.size.IItemSizeAndWeight;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class ItemBase extends Item implements IItemSizeAndWeight {
    /**
     * This should NOT be overridden except for VERY SPECIAL cases
     * If an item needs to not stack, i.e. small vessels, override {@link IItemSizeAndWeight#canStack(ItemStack)}
     * If an item needs a variable stack size, override {@link IItemSizeAndWeight#getWeight(ItemStack)} / {@link IItemSizeAndWeight#getSize(ItemStack)} and return a different value to get a different stack size
     */
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }
}
