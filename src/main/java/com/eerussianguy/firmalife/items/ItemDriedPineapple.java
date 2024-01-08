package com.eerussianguy.firmalife.items;

import com.eerussianguy.firmalife.init.FoodFL;
import com.eerussianguy.firmalife.registry.ItemsFL;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemMisc;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemDriedPineapple extends ItemMisc {
	public ItemDriedPineapple() {
		super(Size.SMALL, Weight.LIGHT);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(ItemsFL.getFood(FoodFL.PINEAPPLE_CHUNKS));
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
}
