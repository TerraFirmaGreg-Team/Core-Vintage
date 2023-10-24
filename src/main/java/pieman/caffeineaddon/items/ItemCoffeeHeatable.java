package pieman.caffeineaddon.items;

import javax.annotation.Nullable;

import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.food.FoodHeatHandler;
import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.util.agriculture.Food;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModItems;
import pieman.caffeineaddon.util.IHasModel;

public class ItemCoffeeHeatable extends ItemCoffee {
	
	public ItemCoffeeHeatable(String name) {
		super(name);
	}

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt){
        return new ItemHeatHandler(nbt, 1.0f, 1599f);
    }

}
