package tfcflorae.objects.items;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public abstract class ItemTFCF extends Item implements IItemSize {
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return getStackSize(stack);
	}
}
