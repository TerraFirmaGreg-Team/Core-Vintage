package su.terrafirmagreg.api.spi.item;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoReg;


public abstract class ItemBase extends Item implements IItemSize, IAutoReg {

	/**
	 * This should NOT be overridden except for VERY SPECIAL cases
	 * If an item needs to not stack, i.e. small vessels, override {@link IItemSize#canStack(ItemStack)}
	 * If an item needs a variable stack size, override {@link IItemSize#getWeight(ItemStack)} / {@link IItemSize#getSize(ItemStack)} and return a different value to get a different stack size
	 */
	@Override
	public int getItemStackLimit(@NotNull ItemStack stack) {
		return getStackSize(stack);
	}
}
