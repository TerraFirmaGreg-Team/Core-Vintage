package com.eerussianguy.firmalife.items;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.objects.items.food.ItemSandwich;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class ItemSandwichFL extends ItemFoodFL {
	private final FoodData data;

	public ItemSandwichFL(FoodData data) {
		super(data);
		this.data = data;
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new ItemSandwich.SandwichHandler(nbt, data);
	}
}
