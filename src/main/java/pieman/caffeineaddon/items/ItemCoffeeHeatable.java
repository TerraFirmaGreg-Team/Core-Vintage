package pieman.caffeineaddon.items;

import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

public class ItemCoffeeHeatable extends ItemCoffee {

	public ItemCoffeeHeatable(String name) {
		super(name);
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new ItemHeatHandler(nbt, 1.0f, 1599f);
	}

}
